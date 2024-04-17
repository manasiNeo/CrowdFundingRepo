import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuccessStoryService {

    @Autowired
    private SuccessStoryRepository successStoryRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    // Retrieve all success stories
    public List<SuccessStory> getAllSuccessStories() {
        return successStoryRepository.findAll();
    }

    // Retrieve a success story by its ID
    public SuccessStory getSuccessStoryById(Long id) {
        return successStoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Success story not found"));
    }

    // Create a new success story
    public SuccessStory createSuccessStory(SuccessStory successStory) {
        return successStoryRepository.save(successStory);
    }

    // Delete a success story by its ID
    public void deleteSuccessStory(Long id) {
        successStoryRepository.deleteById(id);
    }

    // Update a success story
    public SuccessStory updateSuccessStory(Long id, SuccessStory updatedSuccessStory) {
        SuccessStory successStory = successStoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Success story not found"));

        successStory.setTitle(updatedSuccessStory.getTitle());
        successStory.setDescription(updatedSuccessStory.getDescription());
        successStory.setRaisedAmount(updatedSuccessStory.getRaisedAmount());
        // Update other fields as needed

        return successStoryRepository.save(successStory);
    }

    // Update success story when a donation is finished
    public void updateSuccessStoryWithFinishedDonation(Long campaignId) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new RuntimeException("Campaign not found"));

        if (campaign.getStatus().equals("finished")) {
            SuccessStory successStory = successStoryRepository.findByCampaignId(campaignId);
            if (successStory == null) {
                successStory = new SuccessStory();
                successStory.setCampaign(campaign);
            }
            successStory.setTitle(campaign.getTitle());
            successStory.setDescription(campaign.getDescription());
            successStory.setRaisedAmount(campaign.getRaisedAmount());
            // Update other fields as needed

            successStoryRepository.save(successStory);
        }
    }
}
