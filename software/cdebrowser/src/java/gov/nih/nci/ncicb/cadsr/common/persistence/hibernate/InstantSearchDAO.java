package gov.nih.nci.ncicb.cadsr.common.persistence.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

public class InstantSearchDAO  {

	private static InstantSearchDAO _instance;
	private static Session sess;
	
	private InstantSearchDAO() {
		
	}
	
	public static InstantSearchDAO getInstance() {
		if (_instance == null) {
			_instance = new InstantSearchDAO();
		}
		return _instance;
	}
	
	public synchronized List<DENames> getMatchedDEs(final String searchStr) {
		
		Criteria crit = getSession().createCriteria(DENames.class)
									.add(Expression.ilike("longName", searchStr, MatchMode.ANYWHERE))
									.addOrder(Order.asc("longName"));
								

		List<DENames> results = (List<DENames>)crit.list();
		return results;
	}
	
	private Session getSession() {
		if (sess == null) {
			SessionFactory sessFactory = new Configuration()
			.configure()
			.buildSessionFactory();
			sess = sessFactory.openSession();
		}
		
		return sess;
	}
}
