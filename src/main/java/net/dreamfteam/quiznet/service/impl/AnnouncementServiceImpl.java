package net.dreamfteam.quiznet.service.impl;

import net.dreamfteam.quiznet.configs.security.AuthenticationFacade;
import net.dreamfteam.quiznet.data.dao.AnnouncementDao;
import net.dreamfteam.quiznet.data.entities.Announcement;
import net.dreamfteam.quiznet.exception.ValidationException;
import net.dreamfteam.quiznet.service.AnnouncementService;
import net.dreamfteam.quiznet.service.SettingsService;
import net.dreamfteam.quiznet.web.dto.DtoAnnouncement;
import net.dreamfteam.quiznet.web.dto.DtoEditAnnouncement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    AnnouncementDao announcementDao;
    AuthenticationFacade authenticationFacade;
    SettingsService settingsService;

    @Autowired
    public AnnouncementServiceImpl(AnnouncementDao announcementDao,
                                   AuthenticationFacade authenticationFacade, SettingsService settingsService
    ) {
        this.announcementDao = announcementDao;
        this.authenticationFacade = authenticationFacade;
        this.settingsService = settingsService;
    }

    @Override
    public Announcement createAnnouncement(DtoAnnouncement ann, MultipartFile image) {


        Announcement announcement = Announcement.builder()
                .creatorId(authenticationFacade.getUserId())
                .title(ann.getTitle())
                .textContent(ann.getTextContent())
                .creationDate(Calendar.getInstance().getTime())
                .publicationDate(Calendar.getInstance().getTime())
                .isPublished(true).build();

        if (image != null) {
            try {
                announcement.setImage(image.getBytes());
            } catch (IOException e) {
                throw new ValidationException("Broken image");
            }
        } else {
            announcement.setImage(null);
        }

        return announcementDao.createAnnouncement(announcement);
    }

    @Override
    public Announcement getAnnouncement(String announcementId) {
        return announcementDao.getAnnouncement(announcementId);
    }

    @Override
    public List<Announcement> getAllAnnouncements(long start, long amount, String userId) {
        if (userId == null) {
            return announcementDao.getAllAnnouncements(start, amount);
        }

        return null;
    }

    @Override
    public Announcement editAnnouncement(DtoEditAnnouncement ann, MultipartFile image, boolean newImage) {


        Announcement announcement = Announcement.builder().announcementId(ann.getAnnouncementId())
                .creatorId(authenticationFacade.getUserId())
                .title(ann.getTitle())
                .textContent(ann.getTextContent())
                .creationDate(Calendar.getInstance().getTime())
                .publicationDate(Calendar.getInstance().getTime())
                .isPublished(true).build();

        if (image != null) {
            try {
                announcement.setImage(image.getBytes());
            } catch (IOException e) {
                throw new ValidationException("Broken image");
            }
        } else {
            announcement.setImage(null);
        }

        return announcementDao.editAnnouncement(announcement, newImage);
    }

    @Override
    public void deleteAnnouncementById(String id) {
        announcementDao.deleteAnnouncementById(id);
    }

    @Override
    public long getAmount() {
        return announcementDao.getAmount();
    }

}
