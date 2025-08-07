INSERT INTO products (id, active, default_price, client_price, description, client_descriptions, name, created, updated) VALUES
(
  'prod_S866Rn1WSNGxxv',
  true,
  'price_1RDpuf2VoRElKCJv1X389cgT',
  '9.90',
  NULL,
  '[{"icon": "check", "text": "Até 5 vagas todos os dias."}, {"icon": "check", "text": "Envio de vagas 1x ao dia."}, {"icon": "cancel", "text": "Prioridade nas vagas: Receba com antecedência."}]'::jsonb,
  'Essentials',
  to_timestamp(1744648272),
  to_timestamp(1744648273)
),
(
  'prod_S867rCJc3K7ObN',
  true,
  'price_1RDpv22VoRElKCJvmybyo75h',
  '14.90',
  NULL,
  '[{"text": "Até 10 vagas todos os dias.", "icon": "check"},{"text": "Envio de vagas 2x ao dia.", "icon": "check"},{"text": "Prioridade nas vagas: Receba com antecedência.", "icon": "check"}]'::jsonb,
  'Turbo',
  to_timestamp(1744648296),
  to_timestamp(1744648297)
);