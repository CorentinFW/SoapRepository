//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.3.0 
// Voir <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2025.11.17 à 01:29:33 PM CET 
//


package org.tp1.client.wsdl.agence;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="client" type="{http://tp1.org/agence/soap}client"/&gt;
 *         &lt;element name="chambreId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="dateArrive" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dateDepart" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "client",
    "chambreId",
    "dateArrive",
    "dateDepart"
})
@XmlRootElement(name = "effectuerReservationRequest")
public class EffectuerReservationRequest {

    @XmlElement(required = true)
    protected Client client;
    protected int chambreId;
    @XmlElement(required = true)
    protected String dateArrive;
    @XmlElement(required = true)
    protected String dateDepart;

    /**
     * Obtient la valeur de la propriété client.
     * 
     * @return
     *     possible object is
     *     {@link Client }
     *     
     */
    public Client getClient() {
        return client;
    }

    /**
     * Définit la valeur de la propriété client.
     * 
     * @param value
     *     allowed object is
     *     {@link Client }
     *     
     */
    public void setClient(Client value) {
        this.client = value;
    }

    /**
     * Obtient la valeur de la propriété chambreId.
     * 
     */
    public int getChambreId() {
        return chambreId;
    }

    /**
     * Définit la valeur de la propriété chambreId.
     * 
     */
    public void setChambreId(int value) {
        this.chambreId = value;
    }

    /**
     * Obtient la valeur de la propriété dateArrive.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateArrive() {
        return dateArrive;
    }

    /**
     * Définit la valeur de la propriété dateArrive.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateArrive(String value) {
        this.dateArrive = value;
    }

    /**
     * Obtient la valeur de la propriété dateDepart.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateDepart() {
        return dateDepart;
    }

    /**
     * Définit la valeur de la propriété dateDepart.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateDepart(String value) {
        this.dateDepart = value;
    }

}
