package pl.styall.library.core.ext;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.UUIDGenerationStrategy;
import org.hibernate.id.uuid.StandardRandomStrategy;
import org.hibernate.internal.CoreMessageLogger;
import org.hibernate.internal.util.ReflectHelper;
import org.hibernate.type.Type;
import org.hibernate.type.descriptor.java.UUIDTypeDescriptor;
import org.jboss.logging.Logger;

public class Base64UUIDGenerator implements IdentifierGenerator, Configurable {
	public static final String UUID_GEN_STRATEGY = "uuid_gen_strategy";
	public static final String UUID_GEN_STRATEGY_CLASS = "uuid_gen_strategy_class";

    private static final CoreMessageLogger LOG = Logger.getMessageLogger(CoreMessageLogger.class, Base64UUIDGenerator.class.getName());

	private UUIDGenerationStrategy strategy;
	private UUIDTypeDescriptor.ValueTransformer valueTransformer;

	public static Base64UUIDGenerator buildSessionFactoryUniqueIdentifierGenerator() {
		final Base64UUIDGenerator generator = new Base64UUIDGenerator();
		generator.strategy = StandardRandomStrategy.INSTANCE;
		generator.valueTransformer = UUIDTypeDescriptor.ToStringTransformer.INSTANCE;
		return generator;
	}

	public void configure(Type type, Properties params, Dialect d) throws MappingException {
		// check first for the strategy instance
		strategy = (UUIDGenerationStrategy) params.get( UUID_GEN_STRATEGY );
		if ( strategy == null ) {
			// next check for the strategy class
			final String strategyClassName = params.getProperty( UUID_GEN_STRATEGY_CLASS );
			if ( strategyClassName != null ) {
				try {
					final Class strategyClass = ReflectHelper.classForName( strategyClassName );
					try {
						strategy = (UUIDGenerationStrategy) strategyClass.newInstance();
					}
					catch ( Exception ignore ) {
                        LOG.unableToInstantiateUuidGenerationStrategy(ignore);
					}
				}
				catch ( ClassNotFoundException ignore ) {
                    LOG.unableToLocateUuidGenerationStrategy(strategyClassName);
				}
			}
		}
		if ( strategy == null ) {
			// lastly use the standard random generator
			strategy = StandardRandomStrategy.INSTANCE;
		}

		if ( UUID.class.isAssignableFrom( type.getReturnedClass() ) ) {
			valueTransformer = UUIDTypeDescriptor.PassThroughTransformer.INSTANCE;
		}
		else if ( String.class.isAssignableFrom( type.getReturnedClass() ) ) {
			valueTransformer = UUIDTypeDescriptor.ToStringTransformer.INSTANCE;
		}
		else if ( byte[].class.isAssignableFrom( type.getReturnedClass() ) ) {
			valueTransformer = UUIDTypeDescriptor.ToBytesTransformer.INSTANCE;
		}
		else {
			throw new HibernateException( "Unanticipated return type [" + type.getReturnedClass().getName() + "] for UUID conversion" );
		}
	}

	@Override
	public Serializable generate(SessionImplementor session, Object object)
			throws HibernateException {
		ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
		UUID uuid = (UUID) strategy.generateUUID( session ) ;
		buffer.putLong(uuid.getMostSignificantBits());
        buffer.putLong(uuid.getLeastSignificantBits());
        String base64 = new String(Base64.encodeBase64(buffer.array())).replace("=", "+").replace("/", "_");  
        return base64;
	}

	

}
