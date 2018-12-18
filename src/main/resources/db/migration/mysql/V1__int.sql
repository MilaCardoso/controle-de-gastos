CREATE TABLE `gastos` (
  `id` bigint(20) NOT NULL,
  `data` datetime NOT NULL,
  `descricao` varchar(255) NOT NULL,
  `valor` float DEFAULT NULL,
  `tipo` varchar(255) NOT NULL,
  `data_atualizacao` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- AUTO_INCREMENT for table `gastos`
--
ALTER TABLE `gastos`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;