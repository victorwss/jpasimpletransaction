package ninja.javahacker.jpasimpletransactions.eclipselink;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Driver;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;
import ninja.javahacker.jpasimpletransactions.config.ProviderConnectorFactory;
import ninja.javahacker.jpasimpletransactions.config.SchemaGenerationAction;
import ninja.javahacker.jpasimpletransactions.config.SchemaGenerationActionTarget;
import ninja.javahacker.jpasimpletransactions.config.SchemaGenerationSource;
import ninja.javahacker.jpasimpletransactions.config.TriBoolean;

/**
 * A collection of properties used to instantiate a {@link Connector}.
 * @author Victor Williams Stafusa da Silva
 */
@Value
@Wither
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EclipselinkConnectorFactory implements ProviderConnectorFactory<EclipselinkConnectorFactory> {

    private static final Optional<URL> NOWHERE;

    public static final EclipselinkAdapter CANONICAL = new EclipselinkAdapter();

    static {
        try {
            NOWHERE = Optional.of(new URL("http://0.0.0.0/"));
        } catch (MalformedURLException x) {
            throw new AssertionError(x);
        }
    }

    @NonNull String persistenceUnitName;
    @NonNull Class<? extends Driver> driver;
    @NonNull String url;
    @NonNull String user;
    @NonNull String password;
    @NonNull SchemaGenerationAction schemaGenerationAction;
    @NonNull SchemaGenerationSource schemaGenerationCreate;
    @NonNull SchemaGenerationSource schemaGenerationDrop;
    @NonNull SchemaGenerationActionTarget schemaGenerationScriptsCreate;
    @NonNull String loadScript;
    @NonNull String schemaGenerationConnection;
    @NonNull TriBoolean createDatabaseSchemas;
    @NonNull String databaseProductName;
    @NonNull String databaseMajorVersion;
    @NonNull String databaseMinorVersion;
    @NonNull Map<String, String> extras;
    @NonNull List<Class<?>> entities;

    public EclipselinkConnectorFactory() {
        this.persistenceUnitName = "";
        this.driver = Driver.class;
        this.url = "";
        this.user = "";
        this.password = "";
        this.schemaGenerationAction = SchemaGenerationAction.unspecified();
        this.schemaGenerationCreate = SchemaGenerationSource.unspecified();
        this.schemaGenerationDrop = schemaGenerationCreate;
        this.schemaGenerationScriptsCreate = SchemaGenerationActionTarget.unspecified();
        this.loadScript = "";
        this.schemaGenerationConnection = "";
        this.createDatabaseSchemas = TriBoolean.UNSPECIFIED;
        this.databaseProductName = "";
        this.databaseMajorVersion = "";
        this.databaseMinorVersion = "";
        this.extras = Map.of();
        this.entities = List.of();
    }

    @Override
    public EclipselinkAdapter getProviderAdapter() {
        return EclipselinkAdapter.CANONICAL;
    }

    @Override
    public Optional<URL> getPersistenceUnitUrl() {
        return NOWHERE;
    }
}