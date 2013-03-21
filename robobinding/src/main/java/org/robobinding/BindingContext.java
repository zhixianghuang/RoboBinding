/**
 * Copyright 2012 Cheng Wei, Robert Taylor
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
package org.robobinding;

import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.presentationmodel.PresentationModelAdapterImpl;

import android.content.Context;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BindingContext
{
	private final BinderImplementorFactory factory;
	private final Context context;
	private final PresentationModelAdapter presentationModelAdapter;
	private final boolean preInitializeViews;

	public BindingContext(BinderImplementorFactory factory, Context context, Object presentationModel, boolean preInitializeViews)
	{
		this.factory = factory;
		this.context = context;
		this.presentationModelAdapter = new PresentationModelAdapterImpl(presentationModel);
		this.preInitializeViews = preInitializeViews;
	}

	public PresentationModelAdapter getPresentationModelAdapter()
	{
		return presentationModelAdapter;
	}

	public Context getContext()
	{
		return context;
	}

	public ItemBinder createItemBinder()
	{
		return new ItemBinder(factory.create());
	}

	public ViewBinder createViewBinder()
	{
		return new ViewBinder(factory.create());
	}

	public boolean shouldPreInitializeViews()
	{
		return preInitializeViews;
	}
}
