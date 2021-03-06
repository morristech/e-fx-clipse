/**
 * <copyright>
 * </copyright>
 *
 */
package at.bestsolution.efxclipse.tooling.css.cssDsl.impl;

import at.bestsolution.efxclipse.tooling.css.cssDsl.CssDslPackage;
import at.bestsolution.efxclipse.tooling.css.cssDsl.CssSelector;
import at.bestsolution.efxclipse.tooling.css.cssDsl.ElementSelector;
import at.bestsolution.efxclipse.tooling.css.cssDsl.UniversalSelector;
import at.bestsolution.efxclipse.tooling.css.cssDsl.simple_selector;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>simple selector</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link at.bestsolution.efxclipse.tooling.css.cssDsl.impl.simple_selectorImpl#getElement <em>Element</em>}</li>
 *   <li>{@link at.bestsolution.efxclipse.tooling.css.cssDsl.impl.simple_selectorImpl#getUniversal <em>Universal</em>}</li>
 *   <li>{@link at.bestsolution.efxclipse.tooling.css.cssDsl.impl.simple_selectorImpl#getSubSelectors <em>Sub Selectors</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class simple_selectorImpl extends MinimalEObjectImpl.Container implements simple_selector
{
  /**
   * The cached value of the '{@link #getElement() <em>Element</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getElement()
   * @generated
   * @ordered
   */
  protected ElementSelector element;

  /**
   * The cached value of the '{@link #getUniversal() <em>Universal</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getUniversal()
   * @generated
   * @ordered
   */
  protected UniversalSelector universal;

  /**
   * The cached value of the '{@link #getSubSelectors() <em>Sub Selectors</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSubSelectors()
   * @generated
   * @ordered
   */
  protected EList<CssSelector> subSelectors;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected simple_selectorImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return CssDslPackage.Literals.SIMPLE_SELECTOR;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ElementSelector getElement()
  {
    return element;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetElement(ElementSelector newElement, NotificationChain msgs)
  {
    ElementSelector oldElement = element;
    element = newElement;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CssDslPackage.SIMPLE_SELECTOR__ELEMENT, oldElement, newElement);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setElement(ElementSelector newElement)
  {
    if (newElement != element)
    {
      NotificationChain msgs = null;
      if (element != null)
        msgs = ((InternalEObject)element).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CssDslPackage.SIMPLE_SELECTOR__ELEMENT, null, msgs);
      if (newElement != null)
        msgs = ((InternalEObject)newElement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CssDslPackage.SIMPLE_SELECTOR__ELEMENT, null, msgs);
      msgs = basicSetElement(newElement, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, CssDslPackage.SIMPLE_SELECTOR__ELEMENT, newElement, newElement));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public UniversalSelector getUniversal()
  {
    return universal;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetUniversal(UniversalSelector newUniversal, NotificationChain msgs)
  {
    UniversalSelector oldUniversal = universal;
    universal = newUniversal;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CssDslPackage.SIMPLE_SELECTOR__UNIVERSAL, oldUniversal, newUniversal);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setUniversal(UniversalSelector newUniversal)
  {
    if (newUniversal != universal)
    {
      NotificationChain msgs = null;
      if (universal != null)
        msgs = ((InternalEObject)universal).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CssDslPackage.SIMPLE_SELECTOR__UNIVERSAL, null, msgs);
      if (newUniversal != null)
        msgs = ((InternalEObject)newUniversal).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CssDslPackage.SIMPLE_SELECTOR__UNIVERSAL, null, msgs);
      msgs = basicSetUniversal(newUniversal, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, CssDslPackage.SIMPLE_SELECTOR__UNIVERSAL, newUniversal, newUniversal));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<CssSelector> getSubSelectors()
  {
    if (subSelectors == null)
    {
      subSelectors = new EObjectContainmentEList<CssSelector>(CssSelector.class, this, CssDslPackage.SIMPLE_SELECTOR__SUB_SELECTORS);
    }
    return subSelectors;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case CssDslPackage.SIMPLE_SELECTOR__ELEMENT:
        return basicSetElement(null, msgs);
      case CssDslPackage.SIMPLE_SELECTOR__UNIVERSAL:
        return basicSetUniversal(null, msgs);
      case CssDslPackage.SIMPLE_SELECTOR__SUB_SELECTORS:
        return ((InternalEList<?>)getSubSelectors()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case CssDslPackage.SIMPLE_SELECTOR__ELEMENT:
        return getElement();
      case CssDslPackage.SIMPLE_SELECTOR__UNIVERSAL:
        return getUniversal();
      case CssDslPackage.SIMPLE_SELECTOR__SUB_SELECTORS:
        return getSubSelectors();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case CssDslPackage.SIMPLE_SELECTOR__ELEMENT:
        setElement((ElementSelector)newValue);
        return;
      case CssDslPackage.SIMPLE_SELECTOR__UNIVERSAL:
        setUniversal((UniversalSelector)newValue);
        return;
      case CssDslPackage.SIMPLE_SELECTOR__SUB_SELECTORS:
        getSubSelectors().clear();
        getSubSelectors().addAll((Collection<? extends CssSelector>)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case CssDslPackage.SIMPLE_SELECTOR__ELEMENT:
        setElement((ElementSelector)null);
        return;
      case CssDslPackage.SIMPLE_SELECTOR__UNIVERSAL:
        setUniversal((UniversalSelector)null);
        return;
      case CssDslPackage.SIMPLE_SELECTOR__SUB_SELECTORS:
        getSubSelectors().clear();
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case CssDslPackage.SIMPLE_SELECTOR__ELEMENT:
        return element != null;
      case CssDslPackage.SIMPLE_SELECTOR__UNIVERSAL:
        return universal != null;
      case CssDslPackage.SIMPLE_SELECTOR__SUB_SELECTORS:
        return subSelectors != null && !subSelectors.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //simple_selectorImpl
