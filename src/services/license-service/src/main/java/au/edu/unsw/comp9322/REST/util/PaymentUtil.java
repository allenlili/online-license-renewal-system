package au.edu.unsw.comp9322.REST.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.edu.unsw.comp9322.REST.constant.Constant;
import au.edu.unsw.comp9322.REST.dto.PaymentDto;
import au.edu.unsw.comp9322.REST.model.Payment;

@Service
public class PaymentUtil {
	@Autowired
	PaymentDto paymentDto;

	@Autowired
	PaymentUtil paymentUtil;

	public PaymentDto toDto(Payment payment, String returnMsg, String errorCode) {
		paymentDto = new PaymentDto();

		// ModelMapper
		if (payment == null || errorCode != null) {
			paymentDto.setReturnMsg(returnMsg);
			paymentDto.setErrorCode(errorCode);
			return paymentDto;
		}

		paymentDto.setId(payment.getId());
		paymentDto.setStatus(payment.getStatus());
		paymentDto.setAmount(payment.getAmount());
		paymentDto.setInitialDate(payment.getInitialDate());
		paymentDto.setPaidDate(payment.getPaidDate());
		paymentDto.setNoticeId(payment.getNoticeId());

		paymentDto.setReturnMsg(returnMsg);
		paymentDto.setLink(paymentUtil.generateAccessLink(payment));

		return paymentDto;
	}

	public String generateAccessLink(Payment payment) {
		return Constant.DOMAIN_NAME + "/driver/payment/" + Long.toString(payment.getId());
	}
}
