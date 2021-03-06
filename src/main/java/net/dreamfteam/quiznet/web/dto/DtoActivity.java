package net.dreamfteam.quiznet.web.dto;

import lombok.*;
import net.dreamfteam.quiznet.data.entities.ActivityType;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class DtoActivity {

	private String content;

	private String contentUk;

	private String userId;

	private ActivityType activityType;

	private String linkInfo;

}
