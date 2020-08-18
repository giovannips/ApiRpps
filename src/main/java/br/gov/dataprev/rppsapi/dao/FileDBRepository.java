package br.gov.dataprev.rppsapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.dataprev.rppsapi.model.FileDB;


@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {

}
