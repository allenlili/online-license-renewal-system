package au.edu.unsw.comp9322.CLIENT.util;

import org.springframework.stereotype.Service;

import au.edu.unsw.comp9322.CLIENT.bean.AddressAndEmailDto;
import au.edu.unsw.comp9322.CLIENT.responseModel.NoticeDto;
import au.edu.unsw.comp9322.CLIENT.sendModel.Notice;

@Service
public class ModelUtil {

	public Notice NoticeDtoToNotice(NoticeDto noticeDto) {
		Notice notice = new Notice(noticeDto.getId(), noticeDto.getUuid(), noticeDto.getStatus(), noticeDto.getLicenseId(), noticeDto.getTmpAddress(), noticeDto.getTmpEmail(), noticeDto.getOfficerId(), noticeDto.getIsExtend());
		return notice;
	}

	public NoticeDto updateAddressEmail(NoticeDto noticeDto, AddressAndEmailDto addressAndEmail) {
		noticeDto.setTmpAddress(addressAndEmail.getPreStreet()+" "+addressAndEmail.getStreetName()+" "+ addressAndEmail.getStreetType()+", "+ addressAndEmail.getSuburb()+", "+ addressAndEmail.getState());
		noticeDto.setTmpEmail(addressAndEmail.getEmail());
		return noticeDto;
	}

}
