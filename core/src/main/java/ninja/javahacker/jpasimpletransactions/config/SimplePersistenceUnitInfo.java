package ninja.javahacker.jpasimpletransactions.config;

import edu.umd.cs.findbugs.annotations.Nullable;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;
import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * A simple minimalist implementation of the {@link PersistenceUnitInfo} interface.
 * @author Victor Williams Stafusa da Silva
 */
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class SimplePersistenceUnitInfo implements PersistenceUnitInfo {

    Optional<URL> url;
    Class<? extends PersistenceProvider> providerClass;
    String persistenceUnitName;
    List<String> classes;
    Map<String, String> properties;

    @SuppressFBWarnings("OI_OPTIONAL_ISSUES_CHECKING_REFERENCE")
    public SimplePersistenceUnitInfo(
            @NonNull Optional<URL> url,
            @NonNull Class<? extends PersistenceProvider> providerClass,
            @NonNull String persistenceUnitName,
            @NonNull Collection<Class<?>> classes,
            @NonNull Map<String, String> properties)
    {
        this.url = url;
        this.providerClass = providerClass;
        this.persistenceUnitName = persistenceUnitName;
        this.classes = classes.stream().map(Class::getName).collect(Collectors.toList());
        this.properties = new HashMap<>();
        this.properties.putAll(properties);
    }

    @Override
    public String getPersistenceUnitName() {
        return persistenceUnitName;
    }

    @Override
    public String getPersistenceProviderClassName() {
        return providerClass.getName();
    }

    @Override
    public PersistenceUnitTransactionType getTransactionType() {
        return PersistenceUnitTransactionType.RESOURCE_LOCAL;
    }

    @Override
    @Nullable
    public DataSource getJtaDataSource() {
        return null;
    }

    @Override
    @Nullable
    public DataSource getNonJtaDataSource() {
        return null;
    }

    @Override
    public List<String> getMappingFileNames() {
        return Collections.emptyList();
    }

    @Override
    public List<URL> getJarFileUrls() {
        try {
            return Collections.list(this.getClass().getClassLoader().getResources(""));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Nullable
    @Override
    public URL getPersistenceUnitRootUrl() {
        return url.orElse(null);
    }

    @Override
    public List<String> getManagedClassNames() {
        return Collections.unmodifiableList(classes);
    }

    @Override
    public boolean excludeUnlistedClasses() {
        return false;
    }

    @Override
    public SharedCacheMode getSharedCacheMode() {
        return SharedCacheMode.UNSPECIFIED;
    }

    @Override
    public ValidationMode getValidationMode() {
        return ValidationMode.AUTO;
    }

    @Override
    public Properties getProperties() {
        Properties p = new Properties();
        p.putAll(properties);
        return p;
    }

    @Override
    public String getPersistenceXMLSchemaVersion() {
        return "2.2";
    }

    @Override
    @Nullable
    public ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    @Override
    public void addTransformer(ClassTransformer transformer) {
    }

    @Override
    @Nullable
    public ClassLoader getNewTempClassLoader() {
        return null;
    }
}
