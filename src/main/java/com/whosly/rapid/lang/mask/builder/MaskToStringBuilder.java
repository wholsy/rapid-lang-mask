package com.whosly.rapid.lang.mask.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

import com.whosly.rapid.lang.mask.annotation.Maskble;
import com.whosly.rapid.lang.mask.internals.WatchServiceConfigLoader;
import lombok.Setter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.whosly.rapid.lang.mask.util.MaskInfoUtil;
import com.whosly.rapid.lang.mask.annotation.Mask;

/**
 * 带信息过滤的ToStringBuilder
 *
 * @author <a href="mailto:yueny09@126.com"> 袁洋 2015年1月15日 下午3:25:32
 * @inc baidu
 * @category tag
 */
@SuppressWarnings("deprecation")
public class MaskToStringBuilder extends ReflectionToStringBuilder {
	/**
	 * 通过反射将对象内容变为字符串，对匹配到的属性进行内容过滤
	 */
	public static String reflectionToString(final Object object) {
		return toString(object);
	}

	/**
	 * 通过反射将对象内容变为字符串，对匹配到的属性进行内容过滤
	 */
	public static String reflectionToString(final Object object, final ToStringStyle style) {
		return toString(object, style);
	}

	/**
	 * 通过反射将对象内容变为字符串，对匹配到的属性进行内容过滤
	 *
	 * @param object
	 *            要输出的对象
	 */
	public static String toString(final Object object) {
		return toString(object, null, null);
	}

	/**
	 * 通过反射将对象内容变为字符串，对匹配到的属性进行内容过滤
	 *
	 * @param object
	 *            要输出的对象
	 * @param maskFields
	 *            要过滤的属性
	 * @return 过滤后
	 */
	public static String toString(final Object object, final String[] maskFields) {
		return toString(object, null, maskFields);
	}

	/**
	 * 通过反射将对象内容变为字符串，对匹配到的属性进行内容过滤
	 *
	 * @param object
	 *            要输出的对象
	 * @param style
	 *            输出的格式
	 */
	public static String toString(final Object object, final ToStringStyle style) {
		final MaskToStringBuilder builder = new MaskToStringBuilder(object, style, null, null, false, false);
		return builder.toString();
	}

	/**
	 * 通过反射将对象内容变为字符串，对匹配到的属性进行内容过滤
	 *
	 * @param object
	 *            要输出的对象
	 * @param style
	 *            输出的格式
	 * @param maskFields
	 *            要过滤的属性
	 * @return 对象内容变为字符串，对匹配到的属性进行内容过滤
	 */
	public static String toString(final Object object, final ToStringStyle style, final String[] maskFields) {
		final MaskToStringBuilder builder = new MaskToStringBuilder(object, style, null, null, false, false);
		if (maskFields != null && maskFields.length > 0) {
			builder.setMaskFieldsDefine(new HashSet<>(Arrays.asList(maskFields)));
		}
		return builder.toString();
	}

	/**
	 * 在配置项之外的自定义过滤属性。最终结果为  maskFieldsDefine + maskFields
	 */
	@Setter
	private Set<String> maskFieldsDefine;

	private Set<String> getEmailFields(){
		return WatchServiceConfigLoader.get().getEmailFields();
	}

	private Set<String> getMaskFields(){
		if(!(maskFieldsDefine == null || maskFieldsDefine.isEmpty())){
			maskFieldsDefine.addAll(WatchServiceConfigLoader.get().getMaskFields());
			return maskFieldsDefine;
		}

		return WatchServiceConfigLoader.get().getMaskFields();
	}

	/**
	 * 构造一个 MaskReflectionToStringBuilder，基本不会使用，请调用toString
	 */
	public <T> MaskToStringBuilder(final T object, final ToStringStyle style, final StringBuffer buffer,
								   final Class<? super T> reflectUpToClass, final boolean outputTransients, final boolean outputStatics) {
		super(object, style, buffer, reflectUpToClass, outputTransients, outputStatics);
	}

	/**
	 * 在设置输出值时过滤匹配到的字段，可以处理数组和Map
	 */
	@Override
	protected void appendFieldsIn(final Class<?> clazz) {
		if (clazz.isArray()) {
			this.reflectionAppendArray(this.getObject());
			return;
		}

		if (Map.class.isAssignableFrom(clazz) && !Modifier.isAbstract(clazz.getModifiers())) {
			this.reflectionAppendMap((Map<?, ?>) this.getObject());
			return;
		} else if (Map.class.isAssignableFrom(clazz) && Modifier.isAbstract(clazz.getModifiers())) {
			return;
		}

		// clazz is not Array and Map
		final Field[] fields = clazz.getDeclaredFields();
		AccessibleObject.setAccessible(fields, true);
		for (final Field field : fields) {
			final String fieldName = field.getName();
			if (!this.accept(field)) {
				continue;
			}

			try {
				// Warning: Field.get(Object) creates wrappers objects for
				// primitive types.
				final Object fieldValue = this.getValue(field, fieldName);

				this.append(fieldName, fieldValue);
			} catch (final IllegalAccessException ex) {
				// this can't happen. Would get a Security exception instead
				// throw a runtime exception in case the impossible happens.
				throw new InternalError("Unexpected IllegalAccessException: " + ex.getMessage());
			}
		}
	}

	/**
	 * 隐私信息打马赛克
	 *
	 * @param fieldName
	 * @return
	 */
	private Object getValue(final Field field, final String fieldName)
			throws IllegalArgumentException, IllegalAccessException {
		final Object fieldValue = this.getValue(field);

		if (fieldValue == null) {
			return fieldValue;
		}

		Maskble maskble = null;
		// 兼容
		Mask mask = null;
		final Annotation[] annotations = field.getAnnotations();
		if (ArrayUtils.isNotEmpty(annotations)) {
			for (final Annotation annotation : annotations) {
				if (!(annotation instanceof Maskble) && !(annotation instanceof Mask)) {
					continue;
				}

				// Maskble or Mask
				if (fieldValue instanceof String) {
					if (annotation instanceof Maskble) {
						maskble = (Maskble) annotation;
					}else{
						mask = (Mask) annotation;
					}
				}
			}
		}

		// 优先处理 Maskble
		if(maskble != null){
			if(maskble.ignore()){
				// 标记为忽略的，则直接忽略
				return fieldValue;
			}

			// 有标记但不为忽略的，按规则处理
			return MaskInfoUtil.maskSpecial((String) fieldValue, maskble.left(), maskble.right());
		}
		// 兼容 Mask
		if(mask != null){
			if(mask.ignore()){
				// 标记为忽略的，则直接忽略
				return fieldValue;
			}

			// 有标记但不为忽略的，按规则处理
			return MaskInfoUtil.maskSpecial((String) fieldValue, mask.left(), mask.right());
		}

		// 包含在 默认打马赛克的字段列表中
		if (getMaskFields().contains(fieldName)) {
			return MaskInfoUtil.mask(fieldValue.toString());
		}
		// 包含在 默认打马赛克的email字段列表中
		if (getEmailFields().contains(fieldName)) {
			return MaskInfoUtil.maskEmail(fieldValue.toString());
		}

		return fieldValue;
	}

	/**
	 * 对于集合类需要特殊处理. 此处 getStringBuffer() 会重复加载，该方法作废
	 */
	@SuppressWarnings("unchecked")
	@Deprecated
	private void mapToString() {
		final Map<Object, Object> m = (Map<Object, Object>) this.getObject();
		for (final Map.Entry<Object, Object> entry : m.entrySet()) {
			if (entry.getKey() instanceof String && entry.getValue() instanceof String) {
				if (getMaskFields().contains(entry.getKey()) || getEmailFields().contains(entry.getKey())) {
					this.getStringBuffer().append(entry.getKey()).append(MaskInfoUtil.EQUAL)
							.append(MaskInfoUtil.mask((String) entry.getValue())).append(MaskInfoUtil.COMMA);
				}
			}
		}
	}

	/**
	 * 针对Map类型做额外处理
	 */
	protected <K, V> void reflectionAppendMap(final Map<K, V> map) {
		if (map == null || map.isEmpty()) {
			return;
		}
		for (final Map.Entry<K, V> e : map.entrySet()) {
			final Object key = e.getKey();
			Object value = e.getValue();
			if (key != null && value != null && getMaskFields().contains(key.toString())) {
				value = MaskInfoUtil.mask(value.toString());
			} else if (key != null && value != null && getEmailFields().contains(key.toString())) {
				value = MaskInfoUtil.maskEmail(value.toString());
			}
			if (key != null) {
				this.append(key.toString(), value);
			} else {
				this.append("null", value);
			}
		}
	}

	/**
	 * <p>
	 * Gets the String built by this builder.
	 * </p>
	 *
	 * @return the built string
	 */
	@Override
	public String toString() {
//		if (this.getObject() != null && this.getObject() instanceof Map) {
//			mapToString();
//		}

		return super.toString();
	}

}
