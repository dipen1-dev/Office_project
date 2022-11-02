package com.example.loginandregistration.daoImpls;

import com.example.loginandregistration.daos.UserRegistrationDao;
import com.example.loginandregistration.models.User;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@Transactional
public class UserRegistrationDaoImpl  implements UserRegistrationDao {

    @Resource
    EntityManager entityManager;

    @Override
    public boolean saveOrUpdate(User user) {

        Session session = (Session) entityManager.getDelegate();

        try{
            session.saveOrUpdate(user);
            return  true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isAlreadyRegisteredEmail(String email) {
        String queryString = " select count(distinct id) as total from tbl_user where email=:email";

        Integer total = (Integer) ((Session) entityManager.getDelegate())
                .createSQLQuery(queryString)
                .addScalar("total", IntegerType.INSTANCE)
                .setParameter("email", email)
                .uniqueResult();

        return total != 0;

    }

    @Override
    public User findByEmail(String username) {

        String queryString = "select * from tbl_user where email=:email";

        return (User) ((Session)entityManager.getDelegate())
                .createSQLQuery(queryString)
                .setParameter("email", username)
                .setResultTransformer(Transformers.aliasToBean(User.class))
                .uniqueResult();
    }
}
