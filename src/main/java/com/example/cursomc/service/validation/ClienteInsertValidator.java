package com.example.cursomc.service.validation;

import com.example.cursomc.dto.ClienteNewDTO;
import com.example.cursomc.entity.Cliente;
import com.example.cursomc.enums.TipoCliente;
import com.example.cursomc.repository.ClienteRepository;
import com.example.cursomc.resources.exceptions.FieldMessage;
import com.example.cursomc.service.validation.ClienteInsert;
import com.example.cursomc.service.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    @Autowired
    ClienteRepository repo;

    @Override
    public void initialize(ClienteInsert ann) {
    }
    @Override
    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && BR.isValidCPF(objDto.getCpfOuCnpj()))
        {
            list.add(new FieldMessage("cpfouCnpj", "CPF inválido"));
        }

        if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICO.getCod()) && BR.isValidCNPJ(objDto.getCpfOuCnpj()))
        {
            list.add(new FieldMessage("cpfouCnpj", "CNPJ inválido"));
        }

        Cliente aux = repo.findByEmail(objDto.getEmail());
        if (aux != null)
        {
            list.add(new FieldMessage("Email", "Email já existente"));
        }


        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
