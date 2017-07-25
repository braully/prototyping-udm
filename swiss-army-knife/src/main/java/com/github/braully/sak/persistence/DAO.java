package com.github.braully.sak.persistence;

import com.github.braully.sak.util.UtilComparator;
import com.github.braully.sak.util.UtilReflection;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

@SuppressWarnings("rawtypes")
public abstract class DAO implements ICrudEntity, Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger log = Logger.getLogger(UtilComparator.class);

    /* */
    protected abstract EntityManager getEntityManager();

    protected abstract IUser getUserOperation();

    @Override
    public void saveEntityCascade(IEntity e, Collection<? extends IEntity>... relacoes) {
        if (relacoes != null) {
            for (Collection<? extends IEntity> relacao : relacoes) {
                if (relacao != null) {
                    for (IEntity rel : relacao) {
                        this.saveEntity(rel);
                    }
                }
            }
        }
        this.saveEntity(e);
    }

    public void saveTransacionado(IEntity... args) {
        if (args != null) {
            EntityManager em = this.getEntityManager();
            try {
                em.getTransaction().begin();
                for (IEntity e : args) {
                    if (!e.isPersisted()) {
                        this.insert(e);
                    } else {
                        this.update(e);
                    }
                }
                em.getTransaction().commit();
            } catch (RuntimeException e) {
                em.getTransaction().rollback();
                throw e;
            }
        }
    }

    @Override
    public void saveEntitys(IEntity... args) {
        if (args != null) {
            EntityManager em = this.getEntityManager();
            for (IEntity e : args) {
                if (e.isPersisted()) {
                    this.update(e);
                } else {
                    this.insert(e);
                }
            }
        }
    }

    public void saveEntitys(Collection<? extends IEntity> entidades) {
        if (entidades != null) {
            EntityManager em = this.getEntityManager();
            for (IEntity e : entidades) {
                if (e.isPersisted()) {
                    this.update(e);
                } else {
                    this.insert(e);
                }
            }
        }
    }

    @Override
    public void saveEntity(IEntity e) {
        if (e.isPersisted()) {
            this.update(e);
        } else {
            this.insert(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> loadCollection(Class<T> classe) {
        Query query = null;
        List<T> lista = null;
        StringBuilder sb = new StringBuilder();

        sb.append("SELECT c FROM ");
        sb.append(classe.getSimpleName());
        sb.append(" c");

        if (IEntityStatus.class.isAssignableFrom(classe)) {
            sb.append(" WHERE c.status IS NULL OR c.status = ?");
            query = this.getEntityManager().createQuery(sb.toString());
            query.setParameter(1, Status.ACTIVE);
        } else {
            query = this.getEntityManager().createQuery(sb.toString());
        }
        lista = (List<T>) query.getResultList();

        return lista;
    }

    @Override
    public List loadCollection(String nameClasse) {
        Query query = null;
        List lista = null;
        StringBuilder sb = new StringBuilder();

        sb.append("SELECT c FROM ");
        sb.append(nameClasse);
        sb.append(" c");

        query = this.getEntityManager().createQuery(sb.toString());
        lista = query.getResultList();
        return lista;
    }

    @SuppressWarnings("unchecked")
    public <T> int quantidadeEntitys(Class<T> classe) {
        int ret = 0;
        Query query = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT COUNT(c) FROM ");
        sb.append(classe.getSimpleName());
        sb.append(" c");

        if (IEntityStatus.class.isAssignableFrom(classe)) {
            sb.append(" WHERE c.status IS NULL OR c.status = ?");
            query = this.getEntityManager().createQuery(sb.toString());
            query.setParameter(1, Status.ACTIVE);
        } else {
            query = this.getEntityManager().createQuery(sb.toString());
        }
        Number cont = (Number) query.getSingleResult();
        if (cont != null) {
            ret = cont.intValue();
        }
        return ret;
    }

    @Override
    public <T> List<T> loadCollectionSorted(Class<T> classe, String... args) {
        List<T> lista = null;
        if (classe != null) {
            StringBuilder hql = new StringBuilder();
            try {
                hql.append("SELECT DISTINCT e FROM ").append(classe.getSimpleName()).append(" e ");
                Query q = null;
                if (args != null && args.length > 0) {
                    hql.append(" ORDER BY ");

                    for (int i = 0; i < args.length; i = i + 2) {
                        hql.append(args[i]);
                        if (i < args.length - 1) {
                            hql.append(", ");
                        }
                    }
                    q = getEntityManager().createQuery(hql.toString());
                }
                lista = (List<T>) q.getResultList();
            } catch (Exception e) {
                log.error("Erro ao busar", e);
            }
        }
        return lista;
    }

//    @Override
    public <T> List<T> loadCollectionWhere(Class<T> classe, Map parameters) {
        Object[] args = null;
        if (parameters != null) {
            args = new Object[parameters.size() * 2];
            int i = 0;
            Set<Map.Entry> entrys = parameters.entrySet();
            for (Map.Entry e : entrys) {
                args[i++] = e.getKey();
                args[i++] = e.getValue();
            }
//            parameters.forEach((k, v) -> args[i++] = k);//Only java 8
        }
        return loadCollectionWhere(classe, args);
    }

    @Override
    public <T> List<T> loadCollectionWhere(Class<T> classe, Object... args) {
        List<T> lista = null;
        if (classe != null) {
            StringBuilder hql = new StringBuilder();
            try {
                hql.append("SELECT DISTINCT e FROM ").append(classe.getSimpleName()).append(" e ");
                Query q = null;
                if (args != null && args.length > 0 && (args.length % 2) == 0) {
                    hql.append(" WHERE e.").append(args[0]).append(" = ?");

                    for (int i = 2; i < args.length; i = i + 2) {
                        hql.append(" AND e.").append(args[i]).append(" = ?");
                    }

                    q = getEntityManager().createQuery(hql.toString());
                    int j = 1;
                    for (int i = 1; i < args.length; i = i + 2) {
                        hql.append(" AND e.").append(args[i]).append(" = ?");
                        q.setParameter(j++, args[i]);
                    }
                }
                lista = (List<T>) q.getResultList();
            } catch (Exception e) {
                log.error("Erro ao busar", e);
            }
        }
        return lista;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> loadCollectionFetch(Class<T> classe, String... propriedades) {
        List<T> lista = null;
        if (classe != null) {
            StringBuilder sb = this.gerarQueryLoadFetch(classe.getSimpleName(), propriedades);

            if (IEntityStatus.class.isAssignableFrom(classe)) {
                sb.append(" WHERE e.status = ");
                sb.append(Status.ACTIVE.ordinal());
                sb.append(" OR e.status IS NULL");
            }
            lista = (List<T>) this.getEntityManager().createQuery(sb.toString()).getResultList();
        }
        return lista;
    }

    public Object queryObject(Object... args) {
        Object result = null;
        if (args != null && args.length > 0) {
            Query q = queryTemplate(args);
            result = q.getSingleResult();
        }
        return result;
    }

    public List queryList(Object... args) {
        List result = null;
        if (args != null && args.length > 0) {
            Query q = queryTemplate(args);
            result = q.getResultList();
        }
        return result;
    }

    public List queryNativeList(Object... args) {
        List result = null;
        if (args != null && args.length > 0) {
            Query q = this.getEntityManager().createNativeQuery(
                    (String) args[0]);
            for (int i = 1; i < args.length; i++) {
                Object arg = args[i];
                if (arg != null && Date.class.isAssignableFrom(arg.getClass())) {
                    q = q.setParameter(i, (Date) arg, TemporalType.DATE);
                } else {
                    q = q.setParameter(i, arg);
                }
            }
            result = q.getResultList();
        }
        return result;
    }

    protected Query createQuery(String ql) {
        return getEntityManager().createQuery(ql);
    }

    public void insert(Object entity) {
        getEntityManager().persist(entity);
    }

    public <T> void delete(Object id, Class<T> classe) {
        Object entity = getEntityManager().getReference(classe, id);
        this.delete(entity);
    }

    public void delete(Object entity) {
        EntityManager em = this.getEntityManager();
        em.merge(entity);
        getEntityManager().remove(entity);
    }

    public void update(Object entity) {
        getEntityManager().merge(entity);
    }

    public <T> T load(Object id, Class<T> classe) {
        return getEntityManager().find(classe, id);
    }

    @Override
    public <T> T loadEntity(T entidade) {
        try {
            return (T) this.getEntityManager().find(entidade.getClass(), PropertyUtils.getProperty(entidade, "id"));
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            java.util.logging.Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            java.util.logging.Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public <T> T loadEntity(IEntity e, String... propriedades) {
        if (e == null) {
            return null;
        }
        return this.loadEntity(e.getClass().getSimpleName(), e.getId(), propriedades);
    }

    @Override
    public <T> T loadEntity(String nameEntity, Object id, String... propriedades) {
        T result = null;
        String sql = null;
        try {
            StringBuilder query = gerarQueryLoadFetch(nameEntity, propriedades);
            query.append(" WHERE e.id = :id");
            EntityManager em = this.getEntityManager();
            sql = query.toString();
            Query q = em.createQuery(sql);
            q.setParameter("id", id);
            result = (T) q.getSingleResult();
        } catch (RuntimeException ex) {
            log.debug(sql);
            log.error("Falha ao load entidade", ex);
        }
        return result;
    }

    public StringBuilder gerarQueryLoadFetch(String raiz, String... propriedades) {
        StringBuilder sb = new StringBuilder("SELECT DISTINCT e FROM ");
        String abrevRaiz = "e";
        sb.append(raiz);
        sb.append(" ");
        sb.append(abrevRaiz);
        Map<String, String> mapaPropriedades = new HashMap<String, String>();
        mapaPropriedades.put(raiz, abrevRaiz);
        if (propriedades != null && propriedades.length > 0) {
            for (String prop : propriedades) {
                String tmp = gerarAppend(abrevRaiz, prop, mapaPropriedades);
                sb.append(tmp);
            }
        }

        return sb;
    }

    private String gerarAppend(String pai, String prop, Map<String, String> mapaPropriedades) {
        StringBuilder sb = new StringBuilder();
        if (prop.contains(".")) {
            String filho = prop.substring(0, prop.indexOf("."));
            String propFilha = prop.substring(prop.indexOf(".") + 1);
            String paiTmp = mapaPropriedades.get(pai + "_" + filho);
            sb.append(gerarAppend(paiTmp, propFilha, mapaPropriedades));
        } else {
            sb.append(" LEFT JOIN FETCH ");
            sb.append(pai);
            sb.append(".");
            sb.append(prop);
            sb.append(" ");
            sb.append(gerarAbreviacao(pai, prop, mapaPropriedades));
        }
        return sb.toString();
    }

    private String gerarAbreviacao(String pai, String prop, Map<String, String> mapaPropriedades) {
        String chave = pai + "_" + prop;
        String ret = mapaPropriedades.get(chave);
        if (ret == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(prop.charAt(0));
            int ind = 0;
            while (mapaPropriedades.containsValue(sb.toString())) {
                sb.append(ind++);
            }
            ret = sb.toString();
            mapaPropriedades.put(chave, ret);
        }
        return ret;
    }

    private Query queryTemplate(Object... args) {
        Query q = this.getEntityManager().createQuery((String) args[0]);
        for (int i = 1; i < args.length; i++) {
            Object arg = args[i];
            if (arg != null && Date.class.isAssignableFrom(arg.getClass())) {
                q = q.setParameter(i, (Date) arg, TemporalType.DATE);
            } else {
                q = q.setParameter(i, arg);
            }
        }
        return q;
    }

    @Override
    public void saveEntityFlyWeigth(IEntity entidade, String... namePropriedades) {
        if (entidade == null || namePropriedades == null || namePropriedades.length <= 0) {
            throw new IllegalArgumentException();
        }
        if (entidade.isPersisted()) {
            IEntity entidadeTmp = this.loadEntity(entidade);
            if (!UtilComparator.equals(entidade, entidadeTmp, namePropriedades)) {
                entidade.setId(null);
                this.saveEntity(entidade);
            }
        } else {
            String prop = null;
            try {
                Object[] params = new Object[namePropriedades.length * 2];
                for (int i = 0; i < namePropriedades.length; i++) {
                    prop = namePropriedades[i];
                    int j = i * 2;
                    params[j] = prop;
                    params[j + 1] = UtilReflection.getProperty(entidade, prop);
                }
                List<IEntity> colecao = (List<IEntity>) this.loadCollectionWhere(entidade.getClass(), params);
                if (colecao != null && !colecao.isEmpty()) {
                    entidade.setId(colecao.get(0).getId());
                } else {
                    this.saveEntity(entidade);
                }
            } catch (Exception e) {
                String strErro = "Falha ao load propriedade=" + prop + " do objeto=" + entidade;
                throw new IllegalStateException(strErro, e);
            }
        }
    }
}
