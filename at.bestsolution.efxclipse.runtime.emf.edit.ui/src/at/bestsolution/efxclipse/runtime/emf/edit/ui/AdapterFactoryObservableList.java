/******************************************************************************* 
 * Copyright (c) 2012 TESIS DYNAware GmbH and others. 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 *     Torsten Sommer <torsten.sommer@tesis.de> - initial API and implementation 
 *******************************************************************************/
package at.bestsolution.efxclipse.runtime.emf.edit.ui;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;

/**
 * This {@link ObservableList} wraps an {@link AdapterFactory} and retrieves its elements from the
 * adapter-implemented {@link IStructuredItemContentProvider} interface. The content of the list can only be
 * modified through changes to the underlying model.
 */
public class AdapterFactoryObservableList implements ObservableList<Object> {

	protected AdapterFactory adapterFactory;
	protected Object root;
	protected IStructuredItemContentProvider provider;
	/* package */ObservableList<Object> elements = FXCollections.observableArrayList();

	public AdapterFactoryObservableList(AdapterFactory adapterFactory, final Object root) {
		super();

		if (adapterFactory == null)
			throw new IllegalArgumentException("AdapterFactory must not be null.");

		this.adapterFactory = adapterFactory;
		this.root = root;

		provider = getStructuredItemContentProvider();

		elements.addAll(provider.getElements(root));

		if (root instanceof Notifier) {
			AdapterImpl adapter = new AdapterImpl() {

				@Override
				public void notifyChanged(Notification msg) {
					elements.setAll(provider.getElements(root));
				}

			};

			((Notifier) root).eAdapters().add(adapter);
		} else if (root instanceof IChangeNotifier) {
			((IChangeNotifier) root).addListener(new INotifyChangedListener() {

				@Override
				public void notifyChanged(Notification notification) {
					elements.setAll(provider.getElements(root));
				}
			});
		} else {
			throw new IllegalArgumentException("Root object must be a Notifier or IChangeNotifier.");
		}

	}

	@Override
	public boolean add(Object e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(int index, Object element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends Object> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(int index, Collection<? extends Object> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean contains(Object o) {
		return elements.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return elements.containsAll(c);
	}

	@Override
	public Object get(int index) {
		return elements.get(index);
	}

	@Override
	public int indexOf(Object o) {
		return elements.indexOf(o);
	}

	@Override
	public boolean isEmpty() {
		return elements.isEmpty();
	}

	@Override
	public Iterator<Object> iterator() {
		return elements.iterator();
	}

	@Override
	public int lastIndexOf(Object o) {
		return elements.lastIndexOf(o);
	}

	@Override
	public ListIterator<Object> listIterator() {
		return elements.listIterator();
	}

	@Override
	public ListIterator<Object> listIterator(int index) {
		return elements.listIterator(index);
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object remove(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object set(int index, Object element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		return elements.size();
	}

	private IStructuredItemContentProvider getStructuredItemContentProvider() {
		IStructuredItemContentProvider provider = (IStructuredItemContentProvider) adapterFactory.adapt(root,
				IStructuredItemContentProvider.class);
		return provider;
	}

	@Override
	public List<Object> subList(int fromIndex, int toIndex) {
		return elements.subList(fromIndex, toIndex);
	}

	@Override
	public Object[] toArray() {
		return elements.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return elements.toArray(a);
	}

	@Override
	public void addListener(InvalidationListener listener) {
		elements.addListener(listener);
	}

	@Override
	public void removeListener(InvalidationListener listener) {
		elements.removeListener(listener);
	}

	@Override
	public boolean addAll(Object... arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addListener(ListChangeListener<? super Object> listener) {
		elements.addListener(listener);
	}

	@Override
	public void remove(int arg0, int arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Object... arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeListener(ListChangeListener<? super Object> listener) {
		elements.removeListener(listener);
	}

	@Override
	public boolean retainAll(Object... arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean setAll(Object... arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean setAll(Collection<? extends Object> arg0) {
		throw new UnsupportedOperationException();
	}

}