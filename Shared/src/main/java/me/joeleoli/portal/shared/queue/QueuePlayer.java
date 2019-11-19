package me.joeleoli.portal.shared.queue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QueuePlayer implements Comparable {

    private UUID uuid;
    private QueueRank rank;
    private long inserted;

    @Override
    public int compareTo(Object object) {
        int result = 0;

        if (object instanceof QueuePlayer) {
            QueuePlayer otherPlayer = (QueuePlayer) object;
            result = this.rank.getPriority() - otherPlayer.getRank().getPriority();

            if (result == 0) {
                if (this.inserted < otherPlayer.getInserted()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }

        return result;
    }

}
