package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.Nadador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


@Repository
public class NadadorDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* Afegeix el nadador a la base de dades */
    void addNadador(Nadador nadador) {
        jdbcTemplate.update(
                "INSERT INTO Nadador VALUES(?, ?, ?, ?, ?)",
                nadador.getNom(), nadador.getNumFederat(), nadador.getPais(), nadador.getEdat(), nadador.getGenere());
    }

    /* Esborra el nadador de la base de dades */
    void deleteNadador(Nadador nadador) {
        jdbcTemplate.update("DELETE FROM Nadador  WHERE nom = ?;",nadador.getNom());
    }

    /* Actualitza els atributs del nadador
       (excepte el nom, que és la clau primària) */
    void updateNadador(Nadador nadador) {
        jdbcTemplate.update("UPDATE Nadador SET edat = ?, pais = ?,num_Federat = ?, genere = ? WHERE nom= ?;",nadador.getEdat(),nadador.getPais(),nadador.getNumFederat(), nadador.getGenere(),nadador.getNom());

    }

    /* Obté el nadador amb el nom donat. Torna null si no existeix. */
    public Nadador getNadador(String nomNadador) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Nadador WHERE nom =?",
                    new NadadorRowMapper(), nomNadador);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* Obté tots els nadadors. Torna una llista buida si no n'hi ha cap. */
    public List<Nadador> getNadadors() {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM Nadador",
                    new NadadorRowMapper());

        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Nadador>();
        }
    }
}