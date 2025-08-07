CREATE OR REPLACE FUNCTION validate_employment_types(combined text)
RETURNS boolean AS $$
DECLARE
    valid_values TEXT[] := ARRAY['FULLTIME', 'CONTRACTOR', 'PARTTIME', 'INTERN'];
    element TEXT;
BEGIN
    IF combined IS NULL OR combined = '' THEN
        RETURN TRUE;
    END IF;

    FOREACH element IN ARRAY string_to_array(combined, ',')
    LOOP
        IF NOT (element = ANY(valid_values)) THEN
            RETURN FALSE;
        END IF;
    END LOOP;

    RETURN TRUE;
END;
$$ LANGUAGE plpgsql;

DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM pg_constraint
        WHERE conname = 'user_preferences_employment_types_type_check'
    ) THEN
        ALTER TABLE user_preferences
        DROP CONSTRAINT user_preferences_employment_types_type_check;
    END IF;
END
$$;

ALTER TABLE user_preferences_jsearch
ADD CONSTRAINT user_preferences_jsearch_employment_types_valid_values
CHECK (validate_employment_types(employment_types));
