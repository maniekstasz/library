package pl.styall.library.core.ext;

import java.util.List;

import org.springframework.core.GenericTypeResolver;

import pl.styall.library.core.ext.json.serializer.BrowserSerializer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@JsonSerialize(using = BrowserSerializer.class)
public class BrowserWrapper<T > {

	protected final List<T> resultList;
	protected final Long total;

	public BrowserWrapper(List<T> resultList, Long total) {
		super();
		this.resultList = resultList;
		this.total = total;
	}

	public List<T> getResultList() {
		return resultList;
	}

	public Long getTotal() {
		return total;
	}

	@JsonIgnore
	public Class<?> getType() {
		return GenericTypeResolver.resolveTypeArgument(getClass(),
				BrowserWrapper.class);
	}


}
