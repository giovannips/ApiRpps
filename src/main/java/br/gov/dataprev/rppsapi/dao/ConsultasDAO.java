package br.gov.dataprev.rppsapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.dataprev.rppsapi.model.Consulta;

@Repository
public interface ConsultasDAO extends JpaRepository<Consulta, String> {

}
