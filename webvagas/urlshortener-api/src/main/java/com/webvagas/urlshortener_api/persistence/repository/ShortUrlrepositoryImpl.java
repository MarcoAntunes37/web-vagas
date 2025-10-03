package com.webvagas.urlshortener_api.persistence.repository;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.webvagas.urlshortener_api.persistence.repository.projections.ShortUrlBatchInsertProjection;

import jakarta.transaction.Transactional;

@Repository
public class ShortUrlrepositoryImpl implements ShortUrlRepositoryCustom {
    private final JdbcTemplate jdbcTemplate;

    public ShortUrlrepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public List<ShortUrlBatchInsertProjection> insertBatchAndReturn(List<String> codes, List<String> urls) {

        if (codes.isEmpty() || urls.isEmpty() || codes.size() != urls.size()) {
            throw new IllegalArgumentException("Lists must be non-empty and of the same size");
        }

        return jdbcTemplate.execute((Connection conn) -> {
            String sql = """
                        INSERT INTO short_url (code, original_url, created_at)
                        SELECT c, o, now()
                        FROM unnest(?::text[]) WITH ORDINALITY AS t1(c, idx)
                        JOIN unnest(?::text[]) WITH ORDINALITY AS t2(o, idx)
                          ON t1.idx = t2.idx
                        RETURNING original_url, code
                    """;

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                // Criar arrays PostgreSQL
                Array codesArray = conn.createArrayOf("text", codes.toArray());
                Array urlsArray = conn.createArrayOf("text", urls.toArray());

                ps.setArray(1, codesArray);
                ps.setArray(2, urlsArray);

                List<ShortUrlBatchInsertProjection> results = new ArrayList<>();

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String originalUrl = rs.getString("original_url");
                        String code = rs.getString("code");

                        // Criar a projection inline
                        results.add(new ShortUrlBatchInsertProjection() {
                            @Override
                            public String getCode() {
                                return code;
                            }

                            @Override
                            public String getOriginalUrl() {
                                return originalUrl;
                            }
                        });
                    }
                }

                return results;
            }
        });
    }
}
