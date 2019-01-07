CREATE TABLE `transactions` (
  `id` bigint(20) NOT NULL,
  `data` datetime NOT NULL,
  `descricao` varchar(255) NOT NULL,
  `valor` float DEFAULT NULL,
  `tipo_id` bigint(20) DEFAULT NULL
  `data_atualizacao` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- AUTO_INCREMENT for table `gastos`
--
ALTER TABLE `transactions`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;