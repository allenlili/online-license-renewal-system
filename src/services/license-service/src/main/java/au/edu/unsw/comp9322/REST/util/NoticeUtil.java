package au.edu.unsw.comp9322.REST.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.edu.unsw.comp9322.REST.constant.Constant;
import au.edu.unsw.comp9322.REST.dto.NoticeDto;
import au.edu.unsw.comp9322.REST.model.Notice;

@Service
public class NoticeUtil {
	@Autowired
	NoticeDto noticeDto;

	@Autowired
	NoticeUtil noticeUtil;

	public NoticeDto toDto(Notice notice, String returnMsg, String errorCode) {
		noticeDto = new NoticeDto();

		// ModelMapper
		if (notice == null || errorCode != null) {
			noticeDto.setReturnMsg(returnMsg);
			noticeDto.setErrorCode(errorCode);
			return noticeDto;
		}

		noticeDto.setId(notice.getId());
		noticeDto.setUuid(notice.getUuid());
		noticeDto.setStatus(notice.getStatus());
		noticeDto.setLicenseId(notice.getLicenseId());
		noticeDto.setTmpAddress(notice.getTmpAddress());
		noticeDto.setTmpEmail(notice.getTmpEmail());
		noticeDto.setOfficerId(notice.getOfficerId());
		noticeDto.setIsExtend(notice.getIsExtend());
		noticeDto.setRejectReason(notice.getRejectReason());

		noticeDto.setReturnMsg(returnMsg);
		if (notice.getUuid() != null) {
			noticeDto.setLink(noticeUtil.generateAccessLinkForDriver(notice));
		}

		return noticeDto;
	}

	public String generateAccessLinkForOfficer(Notice notice) {
		return Constant.DOMAIN_NAME + "/officer/notice/" + notice.getId();
	}

	public String generateAccessLinkForDriver(Notice notice) {
		return Constant.DOMAIN_NAME + "/driver/notice/uuid/" + notice.getUuid();
	}
}
