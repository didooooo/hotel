package com.tinqinacademy.hotel.persistence.config;

import com.tinqinacademy.hotel.persistence.entity.Bed;
import com.tinqinacademy.hotel.persistence.enums.BedSize;
import com.tinqinacademy.hotel.persistence.repository.interfaces.BedRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class EnumDataConfiguration implements ApplicationRunner {
    private final BedRepository bedRepository;

    public EnumDataConfiguration(BedRepository bedRepository) {
        this.bedRepository = bedRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Bed> fromDataBase = bedRepository.findAll();
        List<String> allCodes = BedSize.getAllCodes();
        for (String code : allCodes) {
            Bed bed = Bed.builder()
                    .type(BedSize.getByCode(code))
                    .count(BedSize.getByCode(code).getCount())
                    .build();
            if(!fromDataBase.contains(bed)){
               bedRepository.save(bed);
            }
        }
    }
}