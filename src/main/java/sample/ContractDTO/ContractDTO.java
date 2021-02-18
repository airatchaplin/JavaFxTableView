package sample.ContractDTO;

import java.util.Date;

public class ContractDTO {

    int id;

    int numberOfContract;

    Date agreementDate;

    Date updatedAgreementDate;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getAgreementDate() {
        return agreementDate;
    }

    public void setAgreementDate(Date agreementDate) {
        this.agreementDate = agreementDate;
    }

    public int getNumberOfContract() {
        return numberOfContract;
    }

    public void setNumberOfContract(int numberOfContract) {
        this.numberOfContract = numberOfContract;
    }

    public Date getUpdatedAgreementDate() {
        return updatedAgreementDate;
    }

    public void setUpdatedAgreementDate(Date updatedAgreementDate) {
        this.updatedAgreementDate = updatedAgreementDate;
    }
}
