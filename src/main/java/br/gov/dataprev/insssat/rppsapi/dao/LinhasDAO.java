package br.gov.dataprev.insssat.rppsapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.dataprev.insssat.rppsapi.entidades.LinhaArquivo;

@Repository
public interface LinhasDAO extends JpaRepository<LinhaArquivo, String> {

}
