package br.gov.dataprev.insssat.rppsapi.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.gov.dataprev.insssat.rppsapi.entidades.LinhaArquivo;

@Repository
public interface LinhasDAO extends JpaRepository<LinhaArquivo, String> {

	@Query("SELECT L FROM LinhaArquivo L WHERE L.idArquivo = :id_arquivo")
	List<LinhaArquivo> obterRetornoArquivo(@Param("id_arquivo") Long idArquivo);
	
	
	@Query("SELECT L FROM LinhaArquivo L where L.cpfServidor = :cpf_servidor AND L.idArquivo IN "+
	"(SELECT MAX(L2.idArquivo) AS idArquivo from LinhaArquivo AS L2 where L2.cpfServidor = :cpf_servidor "+
	"GROUP BY L2.cpfServidor, L2.cnpj) and L.idLinha IN"+
	"(SELECT MAX(L3.idLinha) AS idLinha from LinhaArquivo AS L3 where L3.cpfServidor = :cpf_servidor GROUP BY L3.cpfServidor, L3.cnpj)")
	List<LinhaArquivo> obterLinhasporCPF(@Param("cpf_servidor") Long cpfServidor);

	@Query("SELECT L FROM LinhaArquivo L where L.cpfDependente = :cpf_dependente AND L.idArquivo IN "+
	"(SELECT MAX(L2.idArquivo) AS idArquivo from LinhaArquivo AS L2 where L2.cpfDependente = :cpf_dependente "+
	"GROUP BY L2.cpfDependente, L2.cnpj) and L.idLinha IN"+
	"(SELECT MAX(L3.idLinha) AS idLinha from LinhaArquivo AS L3 where L3.cpfDependente = :cpf_dependente GROUP BY L3.cpfDependente, L3.cnpj)")
	List<LinhaArquivo> obterLinhasporCPFDependente(@Param("cpf_dependente") Long cpfDependente);

}
