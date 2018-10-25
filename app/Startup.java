import com.google.inject.AbstractModule;
import shared.config.DatabaseBootStrap;

public final class Startup extends AbstractModule {

    @Override
    protected void configure() {
        bind(DatabaseBootStrap.class).asEagerSingleton();
    }
}
