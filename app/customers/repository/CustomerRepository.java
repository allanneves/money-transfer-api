package customers.repository;

public interface CustomerRepository {

    Long findId(String nationalId);
}
