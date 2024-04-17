import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/success-stories")
public class SuccessStoryController {

    @Autowired
    private SuccessStoryService successStoryService;

    // Get all success stories
    @GetMapping
    public List<SuccessStory> getAllSuccessStories() {
        return successStoryService.getAllSuccessStories();
    }

    // Get a success story by its ID
    @GetMapping("/{id}")
    public SuccessStory getSuccessStoryById(@PathVariable Long id) {
        return successStoryService.getSuccessStoryById(id);
    }

    // Create a new success story
    @PostMapping
    public SuccessStory createSuccessStory(@RequestBody SuccessStory successStory) {
        return successStoryService.createSuccessStory(successStory);
    }

    // Update an existing success story
    @PutMapping("/{id}")
    public SuccessStory updateSuccessStory(@PathVariable Long id, @RequestBody SuccessStory successStory) {
        return successStoryService.updateSuccessStory(id, successStory);
    }

    // Delete a success story by its ID
    @DeleteMapping("/{id}")
    public void deleteSuccessStory(@PathVariable Long id) {
        successStoryService.deleteSuccessStory(id);
    }
}
