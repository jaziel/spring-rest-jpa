package nz.co.springrestjpa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "customer")
@XmlRootElement(name = "customer")
@XmlAccessorType(XmlAccessType.NONE)
public class Customer implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 8128120823034355117L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @XmlAttribute
  @Column(name = "id")
  private Long id;
  @Column(name = "name")
  @XmlAttribute
  private String name;
  @Column(name = "address")
  @XmlAttribute
  private String address;
  @Column(name = "email")
  @XmlAttribute
  private String email;
  @Column(name = "telephone")
  @XmlAttribute
  private String phone;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  @Override
  public String toString() {
    return "Customer [name=" + name + "]";
  }

}
