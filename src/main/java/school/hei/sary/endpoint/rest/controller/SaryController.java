package school.hei.sary.endpoint.rest.controller;

import static java.io.File.createTempFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.sary.PojaGenerated;
import school.hei.sary.file.BucketComponent;

@RestController
@CrossOrigin
@PojaGenerated
@AllArgsConstructor
public class SaryController {
  BucketComponent bucketComponent;

  @PutMapping("/blacks/{id}")
  public ResponseEntity<String> image_can_be_uploaded_then_signed(
      @RequestBody File file, @PathVariable String id) throws IOException {
    MBFImage image = ImageUtilities.readMBF(file);
    var imageEdited = image.getBand(0);
    var imageFileExtension = ".png";
    var imageFileToUpload = createTempFile(id, imageFileExtension);
    writeImageContent(imageEdited, imageFileToUpload);
    var imageFileBucketKey = id + imageFileExtension;
    can_upload_image_then_download_image(imageFileToUpload, imageFileBucketKey);
    return ResponseEntity.of(Optional.of(can_presign(imageFileBucketKey).toString()));
  }

  @GetMapping("/blacks/{id}")
  public ResponseEntity<Object> image_can_be_got(@PathVariable String id) {
    return ResponseEntity.of(Optional.of(can_presign(id).toString()));
  }

  private void writeImageContent(FImage imageEdited, File imageFile) throws IOException {
    ImageUtilities.write(imageEdited, imageFile);
  }

  private File can_upload_image_then_download_image(File imageFileToUpload, String bucketKey)
      throws IOException {
    bucketComponent.upload(imageFileToUpload, bucketKey);
    var imageFileDownloaded = bucketComponent.download(bucketKey);
    var downloadedContent = Files.readString(imageFileDownloaded.toPath());
    var uploadedContent = Files.readString(imageFileToUpload.toPath());
    if (!uploadedContent.equals(downloadedContent)) {
      throw new RuntimeException("Uploaded and downloaded contents mismatch");
    }

    return imageFileDownloaded;
  }

  private Object can_presign(String imageFileBucketKey) {
    return bucketComponent.presign(imageFileBucketKey, Duration.ofMinutes(2));
  }
}
