CREATE INDEX idx_vacina_bairro_vacina ON vacina_bairro (vacina_id);
CREATE INDEX idx_vacina_bairro_bairro ON vacina_bairro (bairro_id);
CREATE INDEX idx_vacina_bairro_data_aplicacao ON vacina_bairro (data_aplicacao);
CREATE INDEX idx_vacina_bairro_dose ON vacina_bairro (dose);
CREATE INDEX idx_vacina_bairro_aplicador ON vacina_bairro (aplicador);