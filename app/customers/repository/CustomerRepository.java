package customers.repository;

import com.google.inject.ImplementedBy;

@ImplementedBy(CustomerJDBCRepository.class)
public interface CustomerRepository {

    Long findId(String nationalId);
}
