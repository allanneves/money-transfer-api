package transfers.controller;

import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import shared.exceptions.BadRequestException;
import transfers.model.Transfer;
import transfers.model.TransferLog;
import transfers.request.TransferRequestDTO;
import transfers.request.TransferRequestMapper;
import transfers.service.TransferLogService;
import transfers.service.TransferService;

@Slf4j
public final class TransferController extends Controller {

    private final TransferRequestMapper transferRequestMapper;
    private final TransferService transferService;
    private final TransferLogService transferLogService;

    @Inject
    public TransferController(TransferRequestMapper transferRequestMapper, TransferService transferService, TransferLogService transferLogService) {
        this.transferRequestMapper = transferRequestMapper;
        this.transferService = transferService;
        this.transferLogService = transferLogService;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result transfer() {
        final TransferRequestDTO transferRequestDTO = TransferRequestDTO.getFromRequest(request());
        try {
            final Transfer requestedTransfer = transferRequestMapper.transform(transferRequestDTO);
            final Transfer finishedTransfer = transferService.perform(requestedTransfer);
            final TransferLog log = transferLogService.log(finishedTransfer, TransferLogService.Status.SUCCESS.name());
            return created(log.asJson());
        } catch (BadRequestException e) {
            return badRequest(e.asJson());
        }
    }
}
