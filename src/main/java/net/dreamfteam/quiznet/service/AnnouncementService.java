package net.dreamfteam.quiznet.service;

import net.dreamfteam.quiznet.data.entities.Announcement;
import net.dreamfteam.quiznet.web.dto.DtoAnnouncement;
import net.dreamfteam.quiznet.web.dto.DtoEditAnnouncement;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AnnouncementService {

    Announcement createAnnouncement(DtoAnnouncement dtoAnnouncement);

    Announcement getAnnouncement(String announcementId);

    List<Announcement> getAllAnnouncements(long start, long amount, String userId);

    Announcement editAnnouncement(DtoEditAnnouncement dtoAnnouncement);

    void deleteAnnouncementById(String announcementId);

    long getAmount();

    void uploadPicture(MultipartFile file, String id);

}