package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Car;
import com.mycompany.myapp.domain.Manufacturer;
import com.mycompany.myapp.domain.enumeration.Segment;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

/**
 * Created by usu26 on 03/04/2017.
 */
@Repository
public class CarByCriteriaRepository {
    @PersistenceContext
    EntityManager entityManager;

    protected Session currentSession() {
        return entityManager.unwrap(Session.class);
    }

    public List<Car> filteryCarByCriteria(Map<String,Object> parameters, Pageable pageable){
        Criteria carCriteria = currentSession().createCriteria(Car.class);
        carCriteria.setFetchMode("classifications", FetchMode.JOIN);
        carCriteria.setFetchMode("tractions", FetchMode.JOIN);
        carCriteria.setFetchMode("gases", FetchMode.JOIN);
        /** FILTER BY SALES **/
        if(parameters.get("sales")!=null && parameters.get("sales") instanceof Integer){
            filterBySales(parameters,carCriteria);
        }

        /** FILTER BY MODEL **/
        if(parameters.get("model")!=null && parameters.get("model") instanceof String){
            filterByModel(parameters,carCriteria);
        }

        /** FILTER BY SEGMENT **/
        if(parameters.get("segment")!=null){
            filterBySegment(parameters,carCriteria);
        }

        /** FILTER BY MANUFACTURER **/
        Criteria manufacturerCriteria = carCriteria.createCriteria("manufacturer");
        if(parameters.get("manufacturer")!=null && parameters.get("manufacturer") instanceof String){
            filterByManufacturer(parameters,manufacturerCriteria);
        }

        /** FILTER BY PRICES **/
        if((parameters.get("minPrice")!=null && parameters.get("minPrice") instanceof Double)
            && (parameters.get("maxPrice")!=null && parameters.get("maxPrice") instanceof Double)
            ){

            filterByPriceBetween(parameters, carCriteria);
        }

        List<Car> result = carCriteria.list();
        return result;
    }

    private void filterByPriceBetween(Map<String, Object> parameters, Criteria carCriteria) {
        Double minPrice = (Double) parameters.get("minPrice");
        Double maxPrice = (Double) parameters.get("maxPrice");

        carCriteria.add(Restrictions.le("minPrice", minPrice));
        carCriteria.add(Restrictions.le("minPrice", maxPrice));
        carCriteria.add(Restrictions.ge("maxPrice", maxPrice));
        carCriteria.add(Restrictions.ge("maxPrice", minPrice));

    }

    private void filterBySales(Map<String, Object> parameters, Criteria carCriteria) {
        Integer salesInt = (Integer) parameters.get("sales");

        carCriteria.add(Restrictions.eq("sales",salesInt));
    }

    private void filterByModel(Map<String, Object> parameters, Criteria carCriteria) {
        String model = (String) parameters.get("model");

        carCriteria.add(Restrictions.like("model",model, MatchMode.ANYWHERE));
    }

    private void filterBySegment(Map<String, Object> parameters, Criteria carCriteria) {
        filtersBySegmentType(parameters,carCriteria, "segment");
    }

    private void filterByManufacturer(Map<String, Object> parameters, Criteria manufacturerCriteria) {/**TODO test if it works or not*/
        String nameManufacturer = (String) parameters.get("manufacturer");

        manufacturerCriteria.add(Restrictions.like("name",nameManufacturer, MatchMode.ANYWHERE));
    }
    private void filtersBySegmentType(Map<String,Object> parameters, Criteria carCriteria, String key){

        Segment segment = (Segment) parameters.get(key);
        Segment searchSegment = null;
        for(Segment current: searchSegment.values()){
            if(current.toString().equalsIgnoreCase(segment.toString())){
                carCriteria.add(Restrictions.eq(key,searchSegment.valueOf(current.toString())));
            }
        }

    }
}
