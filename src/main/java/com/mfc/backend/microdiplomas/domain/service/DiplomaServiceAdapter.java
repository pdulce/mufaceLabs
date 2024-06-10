package com.mfc.backend.microdiplomas.domain.service;

import com.mfc.backend.microdiplomas.domain.model.Diploma;
import com.mfc.infra.output.adapter.CommandServiceAdapter;
import org.springframework.stereotype.Service;

@Service
public class DiplomaServiceAdapter extends CommandServiceAdapter<Diploma, Long> {

}
