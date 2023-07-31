package id.co.nds.catalogue.services;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import id.co.nds.catalogue.entities.LoanEntity;
import id.co.nds.catalogue.entities.UserEntity;
import id.co.nds.catalogue.exceptions.ClientException;
import id.co.nds.catalogue.exceptions.NotFoundException;
import id.co.nds.catalogue.models.LoanModel;
import id.co.nds.catalogue.repos.LoanRepo;
import id.co.nds.catalogue.repos.UserRepo;
import id.co.nds.catalogue.validators.UserValidator;

@Service
public class LoanService {
    @Autowired
    private LoanRepo loanRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    private UserValidator userValidator = new UserValidator();

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public LoanEntity doLoan(LoanModel loanModel) throws ClientException, NotFoundException {
        userValidator.nullCheckUserId(loanModel.getUserId());
        if(!userRepo.existsById(loanModel.getUserId())) {
            throw new NotFoundException("User not found");
        }

        UserEntity user = userService.findById(loanModel.getUserId());

        if(loanModel.getLoanAmount() == null || loanModel.getLoanTerm() == null || loanModel.getInterestRate() == null) {
            throw new ClientException("Loan amount, loan term, and interest rate must be filled");
        }

        LoanEntity loan = new LoanEntity();
        loan.setUserId(user.getId());
        loan.setRoleId(user.getRoleId());
        loan.setLoanAmount(loanModel.getLoanAmount());
        loan.setLoanTerm(loanModel.getLoanTerm());
        loan.setInterestRate(loanModel.getInterestRate() / 100);
        loan.setTotalLoan(loanModel.getLoanAmount() + (loanModel.getLoanAmount() * (loanModel.getInterestRate() / 100)));
        loan.setCustomerName(user.getFullName());
        loan.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        return loanRepo.save(loan);
    }
}
