package com.mfc.backend.microregalos.domain.service;

import com.mfc.backend.microregalos.domain.model.Regalo;
import com.mfc.infra.output.adapter.CommandServiceAdapter;
import org.springframework.stereotype.Service;

@Service
public class RegaloCommandAdapterService extends CommandServiceAdapter<Regalo, Long> {


}
