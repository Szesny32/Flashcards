package com.flashcards.FlashCards.DB.Repository;

import com.flashcards.FlashCards.DB.Model.Category;
import com.flashcards.FlashCards.DB.Model.Flashcard;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.Optional;

@Getter @Setter
@Repository
public class CategoryRepository {
    @Autowired
    private NamedParameterJdbcTemplate paramTemplate;

    public Category save(String categoryName){
        Optional<Category> category = findByName(categoryName);
        if(category.isEmpty()){
            String sql = "INSERT INTO CATEGORY(cat_name) VALUES (:category_name)";
            SqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("category_name", categoryName);
            paramTemplate.update(sql, namedParameters);
        }
        return findByName(categoryName).orElseThrow(() ->
                new RuntimeException("Failed to retrieve the newly inserted category"));
    }



    public Optional<Category> findById(int id){
        String sql = "SELECT * FROM CATEGORY WHERE cat_name = :category_id";
        SqlParameterSource namedParameters = new MapSqlParameterSource("category_id",id);
        RowMapper<Category> mapper = (ResultSet rs, int rowNum) -> {
            Category category = new Category();
            category.setId(rs.getInt("cat_id"));
            category.setName(rs.getString("cat_name"));
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
            return category;
        };
        try {
            return Optional.ofNullable(paramTemplate.queryForObject(sql, namedParameters, mapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


}
