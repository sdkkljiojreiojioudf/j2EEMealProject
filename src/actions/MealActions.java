package actions;

import entity.MealEntity;
import form.MealView;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.Serializable;
import java.util.List;

@RequestScoped
@Named
public class MealActions extends BasicActions implements Serializable {


    @Inject
    MealView mealView;


    public void insert(){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(mealView.getMeal());
        em.getTransaction().commit();
        em.close();
        mealView.setMeal(new MealEntity());
        mealView.setMealList(getAll());
    }



    public static List<MealEntity> getAll(){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<MealEntity> mealList = em.createQuery("from MealEntity ", MealEntity.class).getResultList();
        em.getTransaction().commit();
        em.close();
        return mealList;
    }
}
