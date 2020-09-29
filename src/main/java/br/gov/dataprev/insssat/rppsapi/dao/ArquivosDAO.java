package br.gov.dataprev.insssat.rppsapi.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.gov.dataprev.insssat.rppsapi.entidades.Arquivo;

@Repository
public interface ArquivosDAO extends JpaRepository<Arquivo, String> {

    @Query("SELECT A FROM Arquivo A WHERE A.cnpjEnte = :nu_cnpj")
    List<Arquivo> obterListaArquivosPorCnpj(@Param("nu_cnpj") Long cnpjEnte);

}
