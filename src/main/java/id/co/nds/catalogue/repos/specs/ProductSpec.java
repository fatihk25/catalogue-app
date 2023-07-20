package id.co.nds.catalogue.repos.specs;

import java.util.function.*;

import javax.persistence.criteria.*;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import id.co.nds.catalogue.entities.ProductEntity;
import id.co.nds.catalogue.globals.GlobalConstant;
import id.co.nds.catalogue.models.ProductModel;

public class ProductSpec implements Specification<ProductEntity> {
    private ProductModel productModel;

    public ProductSpec(ProductModel productModel) {
        super();
        this.productModel = productModel;
    }

    @Override
    public Predicate toPredicate(Root<ProductEntity> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        Predicate p = cb.disjunction();

        if(productModel.getId() != null && productModel.getId() != 0) {
            p.getExpressions().add(cb.equal(root.get("id"), productModel.getId()));
        }

        if(productModel.getName() != null && productModel.getName().length() > 0) {
            p.getExpressions().add(cb.like(cb.lower(root.get("name")),
            "%" + productModel.getName().toLowerCase() + "%"));
        }

        if(productModel.getRecStatus() != null && (productModel.getRecStatus().trim()
        .equalsIgnoreCase(GlobalConstant.REC_STATUS_ACTIVE) || productModel.getRecStatus().trim().equalsIgnoreCase(GlobalConstant.REC_STATUS_NON_ACTIVE))) {
            p.getExpressions().add(cb.equal(cb.upper(root.get("recStatus")), productModel.getRecStatus().toUpperCase()));
        }
        
        cq.orderBy(cb.asc(root.get("id")));
        
        return p;
    } 
}
