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

}
