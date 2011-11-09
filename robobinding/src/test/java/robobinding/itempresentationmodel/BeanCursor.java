/**
 * RowBasedCursor.java
 * Nov 9, 2011 Copyright Cheng Wei and Robert Taylor
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package robobinding.itempresentationmodel;

import java.util.List;

import robobinding.internal.com_google_common.collect.Lists;
import robobinding.internal.java_beans.BeanInfo;
import robobinding.internal.java_beans.IntrospectionException;
import robobinding.internal.java_beans.Introspector;
import robobinding.internal.java_beans.PropertyDescriptor;
import robobinding.internal.org_apache_commons_lang3.Validate;
import robobinding.property.PropertyAccessor;
import robobinding.property.PropertyAccessorUtils;
import android.database.AbstractCursor;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BeanCursor<T> extends AbstractCursor implements TypedCursor<T>
{
	private List<T> beans;
	private List<String> propertyNames;
	public BeanCursor(List<T> beans, Class<T> beanType)
	{
		Validate.notEmpty(beans, "");
		Validate.notNull(beanType);
		
		this.beans = beans;
		initializePropertyNames(beanType);
	}
	private void initializePropertyNames(Class<T> beanType)
	{
		try
		{
			BeanInfo beanInfo = Introspector.getBeanInfo(beanType);
			propertyNames = Lists.newArrayList();
			for(PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors())
			{
				propertyNames.add(propertyDescriptor.getName());
			}
		} catch (IntrospectionException e)
		{
			throw new RuntimeException(e);
		}
	}
	@Override
	public int getCount()
	{
		return beans.size();
	}

	@Override
	public String[] getColumnNames()
	{
		return propertyNames.toArray(new String[0]);
	}

	@Override
	public String getString(int column)
	{
		return (String)getColumnValue(column);
	}

	@Override
	public short getShort(int column)
	{
		return (Short)getColumnValue(column);
	}

	@Override
	public int getInt(int column)
	{
		return (Integer)getColumnValue(column);
	}

	@Override
	public long getLong(int column)
	{
		return (Long)getColumnValue(column);
	}

	@Override
	public float getFloat(int column)
	{
		return (Float)getColumnValue(column);
	}

	@Override
	public double getDouble(int column)
	{
		return (Double)getColumnValue(column);
	}

	@Override
	public boolean isNull(int column)
	{
		Object value = getColumnValue(column);
		return value == null;
	}
	private Object getColumnValue(int column)
	{
		Validate.isTrue(column < propertyNames.size(), "");
		String propertyName = propertyNames.get(column);
		PropertyAccessor<T> propertyAccesor = PropertyAccessorUtils.createPropertyAccessor(getBean(), propertyName);
		return propertyAccesor.getValue(getBean());
	}
	private Object getBean()
	{
		return beans.get(getPosition());
	}
	@Override
	public T getObjectAtPosition(int position)
	{
		Validate.isTrue(position < getCount(), "Invalid position '"+position);
		return beans.get(position);
	}
}