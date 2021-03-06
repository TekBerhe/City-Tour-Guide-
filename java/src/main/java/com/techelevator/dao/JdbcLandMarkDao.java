package com.techelevator.dao;

import com.techelevator.model.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcLandMarkDao implements LandMarkDao{
    private JdbcTemplate jdbcTemplate;
    private PhotoDao photoDao;
    private ReviewDao reviewDao;
    public JdbcLandMarkDao(JdbcTemplate jdbcTemplate, PhotoDao photoDao, ReviewDao reviewDao) {
        this.photoDao = photoDao;
        this.jdbcTemplate = jdbcTemplate;
        this.reviewDao = reviewDao;
    }

    @Override
    public List<LandMark> list() {
        List<LandMark> landMarks = new ArrayList<>();
        String sqlCode = " SELECT landmarks.landmark_id, landmarks.name, landmarks.status, landmarks.content, landmarks.address_id, address_line_1, " +
                "address_line_2, city, state, zipcode, districts.district_name, districts.district_id FROM landmarks " +
                "JOIN address ON address.address_id = landmarks.landmark_id " +
                "JOIN districts ON landmarks.district_id = districts.district_id";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlCode);
        while (results.next()){
            landMarks.add(mapRowToLandmark(results));
        }
        return landMarks;
    }

    @Override
    public LandMark Add(LandmarkDTO landMark) {
        String sql = "INSERT INTO public.address(address_line_1, address_line_2, city, state, zipcode) VALUES(?,?,?,?,?) "
                + "RETURNING address_id ";
        Integer id = jdbcTemplate.queryForObject(sql, Integer.class, landMark.getAddressLineOne(), landMark.getAddressLineTwo(), landMark.getCity(), landMark.getState(),landMark.getZipCode());
        String sql2 = "INSERT INTO landmarks(landmark_id, name, content, address_id, status, district_id) " +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?) RETURNING landmark_id";
        Integer id2 = jdbcTemplate.queryForObject(sql2, Integer.class, landMark.getName(),landMark.getContent(),id,"approved",landMark.getDistrict_id());
        photoDao.add(id2,landMark.getPhotoPath());
        return null;
    }

    @Override
    public List<LandMark> findLandmarksByItineraryId(Long itineraryID) {
        List<LandMark> landmarks = new ArrayList<>();
        String sql = "SELECT landmarks.landmark_id, landmarks.name, landmarks.status, landmarks.content, landmarks.address_id, address_line_1, " +
                "address_line_2, city, state, zipcode, districts.district_name, districts.district_id FROM landmarks " +
                "JOIN address ON address.address_id = landmarks.landmark_id " +
                "JOIN districts ON landmarks.district_id = districts.district_id " +
                "WHERE landmark_id IN " +
                    "(SELECT landmark_id FROM landmarks_itinerary WHERE itinerary_id = ?)";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, itineraryID);
        while (results.next()) {
            landmarks.add(mapRowToLandmark(results));
        }
        return landmarks;
    }

    @Override
    public LandMark get(int landMarkID) {
        return null;
    }

    @Override
    public List<District> getDistricts(){
        return null;
    }

   private LandMark mapRowToLandmark(SqlRowSet row){
        LandMark landMark = new LandMark();
        Address address = new Address();
        District district = new District();
        List<Review> reviews =reviewDao.list(row.getInt("landmark_id")) ;
        landMark.setReview(reviews);
        List<Photo> photos = photoDao.list(row.getInt("landmark_id"));
        landMark.setPhotos(photos);
        landMark.setLandMarkId(row.getInt("landmark_id"));
        landMark.setName(row.getString("name"));
        landMark.setContent(row.getString("content"));
        landMark.setStatus(row.getString("status"));
        address.setAddressId(row.getInt("address_id"));
        address.setAddressLineOne(row.getString("address_line_1"));
        address.setAddressLineTwo(row.getString("address_line_2"));
        address.setState(row.getString("state"));
        address.setCity(row.getString("city"));
        address.setZipCode(row.getInt("zipcode"));
        district.setDistrictId(row.getInt("district_id"));
        district.setName(row.getString("district_name"));
        landMark.setAddress(address);
        landMark.setDistrict(district);
        return landMark;
    }
}
