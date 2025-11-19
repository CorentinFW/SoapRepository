//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.3.0 
// Voir <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2025.11.19 à 08:37:05 AM CET 
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
 *         &lt;element name="adresse" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dateArrive" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dateDepart" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="prixMin" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="prixMax" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="nbrEtoile" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="nbrLits" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
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
    "adresse",
    "dateArrive",
    "dateDepart",
    "prixMin",
    "prixMax",
    "nbrEtoile",
    "nbrLits"
})
@XmlRootElement(name = "rechercherChambresRequest")
public class RechercherChambresRequest {

    protected String adresse;
    @XmlElement(required = true)
    protected String dateArrive;
    @XmlElement(required = true)
    protected String dateDepart;
    protected Float prixMin;
    protected Float prixMax;
    protected Integer nbrEtoile;
    protected Integer nbrLits;

    /**
     * Obtient la valeur de la propriété adresse.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * Définit la valeur de la propriété adresse.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdresse(String value) {
        this.adresse = value;
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

    /**
     * Obtient la valeur de la propriété prixMin.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getPrixMin() {
        return prixMin;
    }

    /**
     * Définit la valeur de la propriété prixMin.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setPrixMin(Float value) {
        this.prixMin = value;
    }

    /**
     * Obtient la valeur de la propriété prixMax.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getPrixMax() {
        return prixMax;
    }

    /**
     * Définit la valeur de la propriété prixMax.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setPrixMax(Float value) {
        this.prixMax = value;
    }

    /**
     * Obtient la valeur de la propriété nbrEtoile.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNbrEtoile() {
        return nbrEtoile;
    }

    /**
     * Définit la valeur de la propriété nbrEtoile.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNbrEtoile(Integer value) {
        this.nbrEtoile = value;
    }

    /**
     * Obtient la valeur de la propriété nbrLits.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNbrLits() {
        return nbrLits;
    }

    /**
     * Définit la valeur de la propriété nbrLits.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNbrLits(Integer value) {
        this.nbrLits = value;
    }

}
