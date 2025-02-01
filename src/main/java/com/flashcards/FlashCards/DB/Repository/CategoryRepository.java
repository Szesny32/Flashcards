package com.flashcards.FlashCards.DB.Repository;

import com.flashcards.FlashCards.DB.Model.Category;
import com.flashcards.FlashCards.DB.Model.Flashcard;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

@Getter @Setter
@Repository
public class CategoryRepository {
    @Autowired
    private NamedParameterJdbcTemplate paramTemplate;

    @Autowired
    JdbcTemplate template;

    public Category save(String categoryName, String path){

            String sql = "INSERT INTO CATEGORY(cat_name, cat_path) VALUES (:category_name, :cat_path)";
            SqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("category_name", categoryName)
                    .addValue("cat_path", path);
            paramTemplate.update(sql, namedParameters);

        return findByName(categoryName).orElseThrow(() ->
                new RuntimeException("Failed to retrieve the newly inserted category"));
    }

    public List<Category> getAll() {
        String sql = "SELECT * FROM CATEGORY";
        RowMapper<Category> mapper = (ResultSet rs, int rowNum) -> {
            Category category = new Category();
            category.setId(rs.getInt("cat_id"));
            category.setName(rs.getString("cat_name"));
            category.setPath(rs.getString("cat_path"));
            return category;
        };
        return template.query(sql, mapper);
    }


    public Optional<Category> findById(int id){
        String sql = "SELECT * FROM CATEGORY WHERE cat_name = :category_id";
        SqlParameterSource namedParameters = new MapSqlParameterSource("category_id",id);
        RowMapper<Category> mapper = (ResultSet rs, int rowNum) -> {
            Category category = new Category();
            category.setId(rs.getInt("cat_id"));
            category.setName(rs.getString("cat_name"));
            category.setPath(rs.getString("cat_path"));
            return category;
        };
        try {
            return Optional.ofNullable(paramTemplate.queryForObject(sql, namedParameters, mapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Category> findByName(String categoryName){
        String sql = "SELECT * FROM CATEGORY WHERE cat_name = :category_name";
        SqlParameterSource namedParameters = new MapSqlParameterSource("category_name",categoryName);
        RowMapper<Category> mapper = (ResultSet rs, int rowNum) -> {
            Category category = new Category();
            category.setId(rs.getInt("cat_id"));
            category.setName(rs.getString("cat_name"));
            category.setPath(rs.getString("cat_path"));
            return category;
        };
        try {
            return Optional.ofNullable(paramTemplate.queryForObject(sql, namedParameters, mapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Category> findAllFamily(String path){
        String sql = "SELECT * FROM CATEGORY WHERE cat_path LIKE = :cat_path";
        SqlParameterSource namedParameters = new MapSqlParameterSource("cat_path",path+'%');
        RowMapper<Category> mapper = (ResultSet rs, int rowNum) -> {
            Category category = new Category();
            category.setId(rs.getInt("cat_id"));
            category.setName(rs.getString("cat_name"));
            category.setPath(rs.getString("cat_path"));
            return category;
        };
        return paramTemplate.query(sql, namedParameters, mapper);
    }




}
