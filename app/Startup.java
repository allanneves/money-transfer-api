import com.google.inject.AbstractModule;

public final class Startup extends AbstractModule {

    @Override
    protected void configure() {
        bind(DatabaseBootStrap.class).asEagerSingleton();
    }
}
