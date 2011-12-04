/**
 * <copyright>
 * </copyright>
 *

 */
package at.bestsolution.efxclipse.tooling.fxml2.fXML.impl;

import at.bestsolution.efxclipse.tooling.fxml2.fXML.ClassDefinition;
import at.bestsolution.efxclipse.tooling.fxml2.fXML.FXMLPackage;
import at.bestsolution.efxclipse.tooling.fxml2.fXML.PropertyDefinition;

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
 * An implementation of the model object '<em><b>Property Definition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link at.bestsolution.efxclipse.tooling.fxml2.fXML.impl.PropertyDefinitionImpl#getName <em>Name</em>}</li>
 *   <li>{@link at.bestsolution.efxclipse.tooling.fxml2.fXML.impl.PropertyDefinitionImpl#getChildren <em>Children</em>}</li>
 *   <li>{@link at.bestsolution.efxclipse.tooling.fxml2.fXML.impl.PropertyDefinitionImpl#getSimpleValue <em>Simple Value</em>}</li>
 *   <li>{@link at.bestsolution.efxclipse.tooling.fxml2.fXML.impl.PropertyDefinitionImpl#getEndname <em>Endname</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PropertyDefinitionImpl extends MinimalEObjectImpl.Container implements PropertyDefinition
{
  /**
   * The default value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected static final String NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected String name = NAME_EDEFAULT;

  /**
   * The cached value of the '{@link #getChildren() <em>Children</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getChildren()
   * @generated
   * @ordered
   */
  protected EList<ClassDefinition> children;

  /**
   * The default value of the '{@link #getSimpleValue() <em>Simple Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSimpleValue()
   * @generated
   * @ordered
   */
  protected static final String SIMPLE_VALUE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getSimpleValue() <em>Simple Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSimpleValue()
   * @generated
   * @ordered
   */
  protected String simpleValue = SIMPLE_VALUE_EDEFAULT;

  /**
   * The default value of the '{@link #getEndname() <em>Endname</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEndname()
   * @generated
   * @ordered
   */
  protected static final String ENDNAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getEndname() <em>Endname</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEndname()
   * @generated
   * @ordered
   */
  protected String endname = ENDNAME_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected PropertyDefinitionImpl()
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
    return FXMLPackage.Literals.PROPERTY_DEFINITION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getName()
  {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setName(String newName)
  {
    String oldName = name;
    name = newName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, FXMLPackage.PROPERTY_DEFINITION__NAME, oldName, name));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<ClassDefinition> getChildren()
  {
    if (children == null)
    {
      children = new EObjectContainmentEList<ClassDefinition>(ClassDefinition.class, this, FXMLPackage.PROPERTY_DEFINITION__CHILDREN);
    }
    return children;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getSimpleValue()
  {
    return simpleValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSimpleValue(String newSimpleValue)
  {
    String oldSimpleValue = simpleValue;
    simpleValue = newSimpleValue;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, FXMLPackage.PROPERTY_DEFINITION__SIMPLE_VALUE, oldSimpleValue, simpleValue));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getEndname()
  {
    return endname;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setEndname(String newEndname)
  {
    String oldEndname = endname;
    endname = newEndname;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, FXMLPackage.PROPERTY_DEFINITION__ENDNAME, oldEndname, endname));
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
      case FXMLPackage.PROPERTY_DEFINITION__CHILDREN:
        return ((InternalEList<?>)getChildren()).basicRemove(otherEnd, msgs);
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
      case FXMLPackage.PROPERTY_DEFINITION__NAME:
        return getName();
      case FXMLPackage.PROPERTY_DEFINITION__CHILDREN:
        return getChildren();
      case FXMLPackage.PROPERTY_DEFINITION__SIMPLE_VALUE:
        return getSimpleValue();
      case FXMLPackage.PROPERTY_DEFINITION__ENDNAME:
        return getEndname();
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
      case FXMLPackage.PROPERTY_DEFINITION__NAME:
        setName((String)newValue);
        return;
      case FXMLPackage.PROPERTY_DEFINITION__CHILDREN:
        getChildren().clear();
        getChildren().addAll((Collection<? extends ClassDefinition>)newValue);
        return;
      case FXMLPackage.PROPERTY_DEFINITION__SIMPLE_VALUE:
        setSimpleValue((String)newValue);
        return;
      case FXMLPackage.PROPERTY_DEFINITION__ENDNAME:
        setEndname((String)newValue);
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
      case FXMLPackage.PROPERTY_DEFINITION__NAME:
        setName(NAME_EDEFAULT);
        return;
      case FXMLPackage.PROPERTY_DEFINITION__CHILDREN:
        getChildren().clear();
        return;
      case FXMLPackage.PROPERTY_DEFINITION__SIMPLE_VALUE:
        setSimpleValue(SIMPLE_VALUE_EDEFAULT);
        return;
      case FXMLPackage.PROPERTY_DEFINITION__ENDNAME:
        setEndname(ENDNAME_EDEFAULT);
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
      case FXMLPackage.PROPERTY_DEFINITION__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case FXMLPackage.PROPERTY_DEFINITION__CHILDREN:
        return children != null && !children.isEmpty();
      case FXMLPackage.PROPERTY_DEFINITION__SIMPLE_VALUE:
        return SIMPLE_VALUE_EDEFAULT == null ? simpleValue != null : !SIMPLE_VALUE_EDEFAULT.equals(simpleValue);
      case FXMLPackage.PROPERTY_DEFINITION__ENDNAME:
        return ENDNAME_EDEFAULT == null ? endname != null : !ENDNAME_EDEFAULT.equals(endname);
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (name: ");
    result.append(name);
    result.append(", simpleValue: ");
    result.append(simpleValue);
    result.append(", endname: ");
    result.append(endname);
    result.append(')');
    return result.toString();
  }

} //PropertyDefinitionImpl
